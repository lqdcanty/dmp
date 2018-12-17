package com.efida.sports.dmp.service.player.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.efida.sport.facade.enums.RegTerminalEnum;
import com.efida.sports.dmp.biz.report.quartz.CleanOpenPlayer;
import com.efida.sports.dmp.dao.entity.ApplyStatistics;
import com.efida.sports.dmp.dao.entity.ApplyStatisticsModel;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenPlayerClean;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.entity.PlayerStatisticalAnalysisModel;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenPlayerCleanMapper;
import com.efida.sports.dmp.enums.ApplyStatisticsTypeEnum;
import com.efida.sports.dmp.service.player.ApplyStatisticsService;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.player.OpenPlayerCleanService;
import com.efida.sports.dmp.utils.IDCardUtil;

import cn.evake.auth.usermodel.PagingResult;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zoutao
 * @since 2018-09-12
 */
@Service
public class OpenPlayerCleanServiceImpl extends ServiceImpl<OpenPlayerCleanMapper, OpenPlayerClean>
                                        implements OpenPlayerCleanService {

    @Autowired
    private OpenPlayerApplyService    openPlayerApplyService;

    @Autowired
    private ApplyStatisticsService    applyStatisticsService;

    private Logger                    logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OpenPlayerCleanMapper     playerCleanMapper;

    @Autowired
    private OpenApplyInfoEntityMapper applyInfoMapper;

    @Override
    public void cleanOpenPlayer() {
        logger.info("start clean open player task");
        ExecutorService executor = Executors.newFixedThreadPool(40);
        long starTime = System.currentTimeMillis();
        int currentPage = 1;
        int pageSize = 1000;
        int currentNum = 0;
        while (true) {
            PagingResult<OpenPlayerEntity> page = openPlayerApplyService
                .selectUnCleanPlayer(currentPage, pageSize);
            if (page == null) {
                break;
            }
            List<OpenPlayerEntity> list = page.getList();
            if (list == null || list.size() < 1) {
                break;
            }
            List<String> applyCodes = new ArrayList<String>();
            for (OpenPlayerEntity openPlayerEntity : list) {
                applyCodes.add(openPlayerEntity.getApplyCode());
            }
            List<OpenApplyInfoEntity> applyInfos = applyInfoMapper
                .selectApplyInfosByCodes(applyCodes);
            Map<String, OpenApplyInfoEntity> applyMap = new HashMap<String, OpenApplyInfoEntity>();
            if (applyInfos.size() > 0) {
                for (OpenApplyInfoEntity openApplyInfoEntity : applyInfos) {
                    applyMap.put(openApplyInfoEntity.getApplyCode(), openApplyInfoEntity);
                }
            }
            for (OpenPlayerEntity item : list) {
                OpenApplyInfoEntity openApplyInfoEntity = null;
                String applyCode = item.getApplyCode();
                if (applyMap.containsKey(applyCode)) {
                    openApplyInfoEntity = applyMap.get(applyCode);
                }
                currentNum++;
                CleanOpenPlayer task = new CleanOpenPlayer(item, openPlayerApplyService,
                    playerCleanMapper, openApplyInfoEntity, this, currentNum);
                executor.execute(task);
            }

            if (list.size() < pageSize) {
                break;
            }
            //            currentPage++;
        }

        long endTime = System.currentTimeMillis();
        long time = endTime - starTime;
        logger.info("清洗运动员数据耗时:" + time + "毫秒");
        logger.info("clean open player task end");
    }

    /**
     * 报名用户分析：
     * 在所有报名数据里区分哪些用户只报名一次，哪些用户报名多次（>=2），
     * 报名多次的用户中，有多少是报名了多个项目（>=2). 团体报名也是根据报名的手机号来判断。
     * 
     */
    @Override
    public void createApplyStatistics() {
        logger.info("start apply statistics task");
        int currentPage = 0;
        int pageSize = 1000;
        long starTime = System.currentTimeMillis();

        Map<String, ApplyStatisticsModel> playerMap = new HashMap<String, ApplyStatisticsModel>();
        while (true) {
            currentPage++;
            Map<String, OpenApplyInfoEntity> applyMap = new HashMap<String, OpenApplyInfoEntity>();
            List<String> applyCodes = new ArrayList<String>();
            PagingResult<OpenPlayerEntity> page = openPlayerApplyService.selectPlayers(currentPage,
                pageSize);
            if (page == null) {
                break;
            }
            logger.info("执行统计报名信息：条数：" + currentPage * pageSize + ",总条数：" + page.getAllRow());
            List<OpenPlayerEntity> list = page.getList();
            if (list == null || list.size() < 1) {
                break;
            }
            for (OpenPlayerEntity item : list) {
                String applyCode = item.getApplyCode();
                applyCodes.add(applyCode);
            }
            if (applyCodes.size() < 1) {
                continue;
            }

            List<OpenApplyInfoEntity> applyInfos = applyInfoMapper
                .selectApplyInfosByCodes(applyCodes);
            if (applyInfos.size() < 1) {
                continue;
            }
            for (OpenApplyInfoEntity openApplyInfoEntity : applyInfos) {
                applyMap.put(openApplyInfoEntity.getApplyCode(), openApplyInfoEntity);
            }
            for (OpenPlayerEntity item : list) {
                String applyCode = item.getApplyCode();
                String key = item.getPlayerPhone() + "_" + item.getPlayerName();
                if (StringUtils.isBlank(applyCode) || !applyMap.containsKey(applyCode)) {
                    continue;
                }
                OpenApplyInfoEntity applyInfo = applyMap.get(applyCode);
                String eventCode = applyInfo.getEventCode();
                if (playerMap.containsKey(key)) {
                    ApplyStatisticsModel statisticsModel = playerMap.get(key);
                    statisticsModel.setApplyCount(statisticsModel.getApplyCount() + 1);
                    Set<String> applyEventCode = statisticsModel.getApplyEventCode();
                    applyEventCode.add(eventCode);
                    statisticsModel.setApplyEventCode(applyEventCode);
                    playerMap.put(key, statisticsModel);
                } else {
                    ApplyStatisticsModel statisticsModel = new ApplyStatisticsModel();
                    statisticsModel.setApplyCount(1);
                    Set<String> applyEventCode = statisticsModel.getApplyEventCode();
                    applyEventCode.add(eventCode);
                    statisticsModel.setApplyEventCode(applyEventCode);
                    playerMap.put(key, statisticsModel);
                }
            }
            if (list.size() < pageSize) {
                break;
            }
        }

        Set<String> keySet = playerMap.keySet();
        long applyOnce = 0;
        long applyMany = 0;
        long applyManyevent = 0;
        for (String key : keySet) {
            ApplyStatisticsModel model = playerMap.get(key);
            long applyCount = model.getApplyCount();
            if (applyCount == 1) {
                applyOnce++;
            } else {
                applyMany++;
                if (model.getApplyEventCount() > 1) {
                    applyManyevent++;
                }
            }
        }
        ApplyStatisticsTypeEnum[] values = ApplyStatisticsTypeEnum.values();
        for (ApplyStatisticsTypeEnum type : values) {
            String code = type.getCode();
            if (ApplyStatisticsTypeEnum.APPLY_ONCE == type) {
                ApplyStatistics applyStatistics = applyStatisticsService
                    .getApplyStatisticsByType(code);
                if (applyStatistics == null) {
                    applyStatistics = new ApplyStatistics();
                }
                applyStatistics.setType(code);
                applyStatistics.setQuantity(applyOnce);
                applyStatisticsService.insertOrUpdate(applyStatistics);
            }
            if (ApplyStatisticsTypeEnum.APPLY_MANY == type) {
                ApplyStatistics applyStatistics = applyStatisticsService
                    .getApplyStatisticsByType(code);
                if (applyStatistics == null) {
                    applyStatistics = new ApplyStatistics();
                }
                applyStatistics.setType(code);
                applyStatistics.setQuantity(applyMany);
                applyStatisticsService.insertOrUpdate(applyStatistics);

            }
            if (ApplyStatisticsTypeEnum.APPLY_MANY_EVENT == type) {
                ApplyStatistics applyStatistics = applyStatisticsService
                    .getApplyStatisticsByType(code);
                if (applyStatistics == null) {
                    applyStatistics = new ApplyStatistics();
                }
                applyStatistics.setType(code);
                applyStatistics.setQuantity(applyManyevent);
                applyStatisticsService.insertOrUpdate(applyStatistics);
            }
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - starTime;
        logger.info("去重后运动员数量：" + playerMap.size() + "统计运动员报名情况耗时:" + time + "毫秒");
        logger.info("apply statistics task end");
    }

    @Override
    public PlayerStatisticalAnalysisModel getSexStatistics() {
        List<Map<String, Object>> list = playerCleanMapper.getSexStatistics();
        PlayerStatisticalAnalysisModel model = new PlayerStatisticalAnalysisModel();
        if (list == null || list.size() < 0) {
            return model;
        }
        for (Map<String, Object> map : list) {
            String sex = map.get("sex").toString();
            if ("m".equalsIgnoreCase(sex)) {
                model.setMaleTotal((long) map.get("total"));
            }
            if ("f".equalsIgnoreCase(sex)) {
                model.setFemaleTotal((long) map.get("total"));
            }
        }
        return model;

    }

    @Override
    public PlayerStatisticalAnalysisModel getAdultStatistics() {
        List<Map<String, Object>> list = playerCleanMapper.getAdultStatistics();
        PlayerStatisticalAnalysisModel model = new PlayerStatisticalAnalysisModel();
        if (list == null || list.size() < 0) {
            return model;
        }
        for (Map<String, Object> map : list) {
            String isAdult = map.get("is_adult").toString();
            if ("1".equals(isAdult)) {
                model.setAdultTotal((long) map.get("total"));
            }
            if ("0".equals(isAdult)) {
                model.setUnAdultTotal((long) map.get("total"));
            }
        }
        return model;
    }

    @Override
    public PlayerStatisticalAnalysisModel getTerminalStatistics() {
        List<Map<String, Object>> list = playerCleanMapper.getTerminalStatistics();
        PlayerStatisticalAnalysisModel model = new PlayerStatisticalAnalysisModel();
        if (list == null || list.size() < 0) {
            return model;
        }
        long otherTotal = 0;
        for (Map<String, Object> map : list) {
            String terminal = map.get("terminal").toString();
            if (RegTerminalEnum.ANDROID.getCode().equals(terminal)) {
                model.setAndroidTotal((long) map.get("total"));
            }
            if (RegTerminalEnum.PC.getCode().equals(terminal)) {
                model.setPcTotal((long) map.get("total"));
            }
            if (RegTerminalEnum.WEICHAT.getCode().equals(terminal)) {
                model.setWeichatTotal((long) map.get("total"));
            }
            if (RegTerminalEnum.IOS.getCode().equals(terminal)) {
                model.setIosTotal((long) map.get("total"));
            } else {
                otherTotal += (long) map.get("total");
            }
        }
        model.setDmpTotal(otherTotal);
        return model;
    }

    @Override
    public OpenPlayerClean getPlayerCleanByPlayerCode(String playerCode) {
        Wrapper<OpenPlayerClean> wrapper = new EntityWrapper<OpenPlayerClean>();
        wrapper.eq("player_code", playerCode);
        return selectOne(wrapper);
    }

    /**
    *是否成年人 1:已成年 0 未成年 null 未知
    * 规则  ： 分组 出现儿童组或者少年组 未成年 家长组属于成年
    *        填写有生日 按生日计算      
    *        未填写生日按省份证计算
    * @param player
    * @param applyInfo
    * @return
    */
    public Integer isAdult(OpenPlayerEntity player, OpenApplyInfoEntity applyInfo) {
        try {
            String groupName = applyInfo.getMatchGroupName();
            if ("少年组".equals(groupName) || "未成年".equals(groupName)) {
                return 0;
            }
            if ("家长组".equals(groupName)) {
                return 1;
            }
            Date applyTime = applyInfo.getApplyTime();
            if (applyTime == null) {
                return null;
            }
            Date playerBirth = player.getPlayerBirth();
            if (playerBirth != null) {
                int age = IDCardUtil.getAgeByDate(playerBirth, applyTime);
                return age >= 18 ? 1 : 0;
            }
            String playerCerType = player.getPlayerCerType();
            if ("1".equals(playerCerType) && IDCardUtil.checkIdCard(player.getPlayerCerNo())) {
                int age = IDCardUtil.getAgeByIdCard(player.getPlayerCerNo(), applyTime);
                return age >= 18 ? 1 : 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public Integer getAge(OpenPlayerEntity player, OpenApplyInfoEntity applyInfo) {
        try {
            Date applyTime = applyInfo.getApplyTime();
            if (applyTime == null) {
                return null;
            }
            Date playerBirth = player.getPlayerBirth();
            if (playerBirth != null) {
                int age = IDCardUtil.getAgeByDate(playerBirth, applyTime);
                return age;
            }
            String playerCerType = player.getPlayerCerType();
            if ("1".equals(playerCerType) && IDCardUtil.checkIdCard(player.getPlayerCerNo())) {
                int age = IDCardUtil.getAgeByIdCard(player.getPlayerCerNo(), applyTime);
                return age;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public String getSex(OpenPlayerEntity player, OpenApplyInfoEntity applyInfo) {
        try {
            String sex = player.getSex();
            if (!StringUtils.isBlank(sex)) {
                if ("m".equalsIgnoreCase(sex) || "男".equals(sex)) {
                    return "m";
                }
                if ("f".equalsIgnoreCase(sex) || "女".equals(sex) || "w".equalsIgnoreCase(sex)) {
                    return "f";
                }
            }
            String playerCerType = player.getPlayerCerType();
            if ("1".equals(playerCerType) && IDCardUtil.checkIdCard(player.getPlayerCerNo())) {
                return IDCardUtil.getGenderByIdCard(player.getPlayerCerNo());
            }
        } catch (Exception e) {
        }

        return null;
    }

}
