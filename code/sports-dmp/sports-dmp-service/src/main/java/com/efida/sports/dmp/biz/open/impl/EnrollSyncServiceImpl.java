/**
 * 
 */
package com.efida.sports.dmp.biz.open.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.efida.sport.admin.facade.model.SpGroupItemModel;
import com.efida.sport.admin.facade.model.SpMatchGroupModel;
import com.efida.sport.admin.facade.model.SpMatchInfoModel;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.ApplyInfoModel;
import com.efida.sport.facade.model.PlayerModel;
import com.efida.sports.dmp.biz.dubbo.integration.ApplyInfoFacadeClient;
import com.efida.sports.dmp.biz.open.EnrollSyncService;
import com.efida.sports.dmp.biz.open.quartz.OpenEnrollSycTask;
import com.efida.sports.dmp.biz.open.quartz.SynTask;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenEnrollInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenPlayerEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;

import cn.evake.auth.service.common.CacheService;

/**
 * @author zhiyang
 *
 */
@Service("enrollSyncService")
public class EnrollSyncServiceImpl implements EnrollSyncService {

    //
    private Logger                    logger               = LoggerFactory
        .getLogger(OpenEnrollSycTask.class);


    @Autowired
    private CacheService              cacheSevcice;

    @Autowired
    private ApplyInfoFacadeClient     applyInfoFacadeClient;

    @Autowired
    private OpenApplyInfoEntityMapper openApplyInfoEntityMapper;

    @Autowired
    private OpenPlayerEntityMapper    openPlayerEntityMapper;

    @Autowired
    private SpMatchFacadeClient       spMatchFacadeClient;
    
    
    public String getLockSycKey(OpenApplyInfoEntity app){
        
        String key = "dmp_enrollsync_c"+app.getApplyCode();
        return key;
    }
    public void syncOneEnrollInfo(OpenApplyInfoEntity app) {
        
       
        String lockKey = getLockSycKey(app);
        String value = cacheSevcice.get(lockKey);
        if(value!=null){
            return ;
        }
        cacheSevcice.put(lockKey, "1", 120);
        OpenApplyInfoEntity newapp = this.openApplyInfoEntityMapper.selectByPrimaryKey(app.getId());
        if(newapp.getSync()!=null&&newapp.getSync()==1){
            logger.warn("已经被其他线程同步:applyCode:"+newapp.getApplyCode()+",eventName:"+newapp.getEventName());
            return;
        }
        String applyCode = app.getApplyCode();
        int syncTotal = app.getSyncTotal() == null ? 1 : app.getSyncTotal() + 1;
        long syncInterval = syncTotal * 30;
        if (syncInterval > 30 * 60) {
            syncInterval = 1800;
        }
        long nexSyncTime = System.currentTimeMillis() + syncInterval * 1000;
        byte sync = 0;
        String syncStatus = "";
        try {
            List<OpenPlayerEntity> items = this.openPlayerEntityMapper
                .selectPlayerByApplyCode(applyCode);
            ApplyInfoModel applyInfo = convert2ApplyInfo(app, items);
            //数据检查
            if (StringUtils.isEmpty(app.getMatchCode())) {
                sync = 3;
                syncStatus = "matchCode 为空！";
                throw new BusinessException(syncStatus);
            }
            if (StringUtils.isEmpty(app.getEventCode())) {
                sync = 3;
                syncStatus = "eventCode 为空！";
                throw new BusinessException(syncStatus);
            }

            /*            if (StringUtils.isEmpty(app.getFieldCode())) {
                sync = 3;
                syncStatus = "fieldCode为空";
                throw new BusinessException(syncStatus);
            }*/

            if (StringUtils.isEmpty(app.getMatchName()) || StringUtils.isEmpty(app.getGameName())) {
                SpMatchInfoModel matchInfo = spMatchFacadeClient.getMatchDetail(app.getMatchCode());
                if (matchInfo != null) {
                    app.setMatchName(matchInfo.getMatchName());
                    app.setGameCode(matchInfo.getGameCode());
                    app.setGameName(matchInfo.getGameName());
                } else {
                    sync = 3;
                    syncStatus = "matchCode未找到赛事配置！";
                    throw new BusinessException(syncStatus);
                }
                if (StringUtils.isEmpty(app.getUnitCode())) {
                }
            }

            //null
            if (StringUtils.isEmpty(app.getFieldName())) {
                //todo
                app.setFieldName("");
            }

            if (StringUtils.isEmpty(app.getMatchGroupName())) {
                if (!StringUtils.isEmpty(app.getMatchGroupCode())) {
                    SpMatchGroupModel group = spMatchFacadeClient
                        .getGroupModel(app.getMatchGroupCode());
                    if (group == null) {
                        sync = 3;
                        syncStatus = "matchGroupCode未找到组配置！";
                        throw new BusinessException(syncStatus);
                    }
                    app.setMatchGroupName(group.getGroupName());
                }
            }

            if (StringUtils.isEmpty(app.getEventName())) {
                SpGroupItemModel eventInfo = spMatchFacadeClient.getEventItem(app.getEventCode());
                if (eventInfo == null) {
                    sync = 3;
                    syncStatus = "未找到eventCode配置";
                    throw new BusinessException(syncStatus);
                }
                app.setEventName(eventInfo.getItemName());
            }

            boolean success = applyInfoFacadeClient.syncApplyInfo(applyInfo);
            if (success) {
                app.setSync((byte) 1);
                app.setSyncStatus("success");
            } else {
                app.setSync((byte) 0);
                app.setSyncStatus("failed");
                app.setNextSyncTime(new Date(nexSyncTime));
            }
        } catch (Exception ex) {
            logger.error("syncApplyInfo failed, applyCode:" + app.getApplyCode() + ",leaderPhone:"
                         + app.getLeaderPhone(),
                ex);
            if (sync != 0) {
                app.setSync(sync);
            } else {
                app.setSync((byte) 0);
            }
            syncStatus = ex.getMessage();
            if (syncStatus != null && syncStatus.length() > 24) {
                syncStatus = syncStatus.substring(0, 24);
            }
            app.setNextSyncTime(new Date(nexSyncTime));
            app.setSyncStatus(syncStatus);
        } finally {
            app.setSyncTotal(syncTotal);
        }
        this.openApplyInfoEntityMapper.updateByPrimaryKeySelective(app);
    }

    private ApplyInfoModel convert2ApplyInfo(OpenApplyInfoEntity app,
                                             List<OpenPlayerEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            throw new BusinessException("运动员信息为空");
        }
        ApplyInfoModel ap = new ApplyInfoModel();
        ap.setIdempotentId(app.getApplyCode());
        ap.setEventType(app.getEventType());
        ap.setLeaderName(ap.getLeaderName());
        ap.setLeaderPhone(ap.getLeaderPhone());
        ap.setTeamName(ap.getTeamName());

        ap.setApplyAmount(app.getApplyAmount());
        ap.setApplyTime(app.getApplyTime());
        ap.setApplyStatus("success");
        ap.setUnitCode(app.getUnitCode());
        ap.setGameCode(app.getGameCode());
        ap.setGameName(app.getGameName());
        ap.setMatchCode(app.getMatchCode());
        ap.setMatchName(app.getMatchName());
        ap.setSiteCode(app.getFieldCode());
        ap.setSiteName(app.getFieldName());

        ap.setMatchGroupCode(app.getMatchGroupCode());
        ap.setMatchGroupName(app.getMatchGroupName());
        ap.setGroupEventCode(app.getMatchGroupCode());

        ap.setEventCode(app.getEventCode());
        ap.setEventName(app.getEventName());
        ap.setEventStartTime(app.getEventStartTime());
        ap.setEventEndTime(app.getEventEndTime());

        List<PlayerModel> players = new ArrayList<PlayerModel>();
        for (OpenPlayerEntity pl : items) {
            PlayerModel pm = convert2player(pl);
            players.add(pm);
        }
        ap.setPlayers(players);
        return ap;
    }

    private PlayerModel convert2player(OpenPlayerEntity pl) {
        PlayerModel pm = new PlayerModel();
        pm.setPlayerCode(pl.getPlayerCode());
        pm.setPlayerPhone(pl.getPlayerPhone());
        pm.setPlayerName(pl.getPlayerName());

        pm.setSex(pl.getSex());
        pm.setEmail(pl.getEmail());
        pm.setPlayerWeight(pl.getPlayerWeight());
        pm.setPlayerHeight(pl.getPlayerHeight());
        pm.setPlayerBirth(pl.getPlayerBirth());
        pm.setPlayerNationality(pl.getPlayerNationality());
        pm.setPlayerAddress(pl.getPlayerAddress());
        pm.setPlayerCerType(pl.getPlayerCerType());
        pm.setPlayerCerNo(pl.getPlayerCerNo());

        pm.setPlayerBloodType(pl.getPlayerBloodType());
        pm.setPlayerNation(pl.getPlayerNation());
        pm.setPlayerClothSize(pl.getPlayerClothSize());
        pm.setPlayerWorkUnit(pl.getPlayerWorkUnit());
        pm.setPlayerEmergencyName(pl.getPlayerEmergencyName());
        pm.setPlayerEmergencyPhone(pl.getPlayerEmergencyPhone());

        pm.setAttUrl(pl.getAttUrl());
        pm.setImgUrl(pl.getImgUrl());

        pm.setExtProp(pl.getExtProp());
        String extProp = pl.getExtProp();
        JSONObject prop = null;
        if (!StringUtils.isEmpty(extProp)) {
            prop = JSONObject.parseObject(extProp);
            pm.setAssettoId(prop.getString("assettoId"));
            prop.remove("assettoId");
        } else {
            prop = new JSONObject();
        }
        prop.put("playerCode", pl.getPlayerCode());
        prop.put("playerCategory", pl.getPlayerCategory());
        prop.put("openPlatType", pl.getOpenPlatType());
        prop.put("openId", pl.getOpenId());
        pm.setExtProp(prop.toJSONString());

        return pm;
    }

    private ApplyInfoModel convert2ApplyInfo(OpenEnrollInfoEntity item) {

        ApplyInfoModel ap = new ApplyInfoModel();

        ap.setApplyAmount(item.getApplyAmount());
        ap.setApplyTime(item.getApplyTime());
        ap.setApplyStatus("success");
        ap.setUnitCode(item.getUnitCode());
        ap.setGameCode(item.getGameCode());
        ap.setGameName(item.getGameName());
        ap.setMatchCode(item.getMatchCode());
        ap.setMatchName(item.getMatchName());
        ap.setSiteCode(item.getFieldCode());
        ap.setSiteName(item.getFieldName());

        ap.setMatchGroupCode(item.getMatchGroupCode());
        ap.setMatchGroupName(item.getMatchGroupName());
        ap.setGroupEventCode(item.getMatchGroupCode());

        ap.setEventCode(item.getEventCode());
        ap.setEventName(item.getEventName());
        ap.setEventStartTime(item.getEventStartTime());
        ap.setEventEndTime(item.getEventEndTime());

        PlayerModel pm = new PlayerModel();
        pm.setPlayerCode(item.getPlayerCode());
        pm.setPlayerPhone(item.getPlayerPhone());
        pm.setPlayerName(item.getPlayerName());

        pm.setSex(item.getSex());
        pm.setEmail(item.getEmail());
        pm.setPlayerWeight(item.getPlayerWeight());
        pm.setPlayerHeight(item.getPlayerHeight());
        pm.setPlayerBirth(item.getPlayerBirth());
        pm.setPlayerNationality(item.getPlayerNationality());
        pm.setPlayerAddress(item.getPlayerAddress());
        pm.setPlayerCerType(item.getPlayerCerType());
        pm.setPlayerCerNo(item.getPlayerCerNo());

        pm.setPlayerBloodType(item.getPlayerBloodType());
        pm.setPlayerNation(item.getPlayerNation());
        pm.setPlayerClothSize(item.getPlayerClothSize());
        pm.setPlayerWorkUnit(item.getPlayerWorkUnit());
        pm.setPlayerEmergencyName(item.getPlayerEmergencyName());
        pm.setPlayerEmergencyPhone(item.getPlayerEmergencyPhone());

        pm.setAttUrl(item.getAttUrl());
        pm.setImgUrl(item.getImgUrl());

        pm.setExtProp(item.getExtProp());
        String extProp = item.getExtProp();
        JSONObject prop = null;
        if (!StringUtils.isEmpty(extProp)) {
            prop = JSONObject.parseObject(extProp);
            pm.setAssettoId(prop.getString("assettoId"));
        } else {
            prop = new JSONObject();
        }
        prop.put("playerCode", item.getPlayerCode());
        prop.put("playerCategory", item.getPlayerCategory());
        prop.put("openPlatType", item.getOpenPlatType());
        prop.put("openId", item.getOpenId());
        pm.setExtProp(prop.toJSONString());
        return ap;
    }

    public List<OpenApplyInfoEntity> selectUnSyncRecord(int pageNumber, int pageSize) {

         return selectUnSyncRecord(null, pageNumber, pageSize);
    }
    
    private List<OpenApplyInfoEntity> selectUnSyncRecord(String unitCode, int pageNumber,
                                                         int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("unitCode", unitCode);
        map.put("start", start);
        map.put("limit", pageSize);
        List<OpenApplyInfoEntity> items = this.openApplyInfoEntityMapper.selectUnSyncRecord(map);

        return items;
    }
    /**
     * 多线程同步
     */
    public void multTreadSync(int maxNumber){
        
        long startTime = System.currentTimeMillis();
        logger.info("start sync maxNumber:"+maxNumber);
        int pageSize =  50;
        ExecutorService executor = Executors.newFixedThreadPool(10);  
        int currentNum = 0;
        boolean isExit = false;
        while(true){
            List<OpenApplyInfoEntity> items = selectUnSyncRecord(1, pageSize);
            if(CollectionUtils.isEmpty(items)){
                break;
            }
            for (OpenApplyInfoEntity item:items) {  
                SynTask worker = new SynTask(item);  
                worker.setSyncService(this);
                executor.execute(worker);  
                currentNum++;
                if(currentNum>=maxNumber){
                    isExit = true;
                    break;
                }
            }  
            
            if(items.size()<pageSize){
                break;
            }
            if(isExit){
                break;
            }
        }
        executor.shutdown();  
        while (!executor.isTerminated()) {  
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                
            }
        }  
        logger.info("end sync  recordNumber:"+currentNum+", spent:"+(System.currentTimeMillis()-startTime)+"ms");
     }
    @Override
    public long multTreadSync2(String unitCode, int pageNumber, int pageSize) {
        
        long startTime = System.currentTimeMillis();
        logger.info("start sync unitCode:"+unitCode+", pageNumber:"+pageNumber+",pageSize:"+pageSize);
         ExecutorService executor = Executors.newFixedThreadPool(10);  
        int currentNum = 0;
        boolean isExit = false;
        while(true){
            List<OpenApplyInfoEntity> items = selectUnSyncRecord(unitCode, pageNumber, pageSize);
            if(CollectionUtils.isEmpty(items)){
                break;
            }
            for (OpenApplyInfoEntity item:items) {  
                SynTask worker = new SynTask(item);  
                worker.setSyncService(this);
                executor.execute(worker);  
                currentNum++;
                if(currentNum>=pageSize){
                    isExit = true;
                    break;
                }
            }  
            
            if(items.size()<pageSize){
                break;
            }
            if(isExit){
                break;
            }
        }
        executor.shutdown();  
        while (!executor.isTerminated()) {  
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                
            }
        }  
        long spent = (System.currentTimeMillis()-startTime);
        logger.info("end multTreadSync2  sync recordNumber:"+currentNum+", spent:"+spent+"ms");
        return spent;
    }

}
