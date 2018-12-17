package com.efida.sports.dmp.biz.report.quartz;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenPlayerClean;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenPlayerCleanMapper;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.player.impl.OpenPlayerCleanServiceImpl;

public class CleanOpenPlayer implements Runnable {

    private static Logger              log  = LoggerFactory.getLogger(CleanOpenPlayer.class);

    private OpenPlayerEntity           data = null;

    private OpenPlayerApplyService     openPlayerApplyService;

    private OpenPlayerCleanMapper      playerCleanMapper;

    private OpenApplyInfoEntityMapper  applyInfoMapper;

    private OpenPlayerCleanServiceImpl openPlayerCleanService;
    private OpenApplyInfoEntity        applyInfo;
    private int                        currentNum;

    public CleanOpenPlayer(OpenPlayerEntity item, OpenPlayerApplyService openPlayerApplyService,
                           OpenPlayerCleanMapper playerCleanMapper, OpenApplyInfoEntity applyInfo,
                           OpenPlayerCleanServiceImpl openPlayerCleanService, int currentNum) {
        this.data = item;
        this.applyInfo = applyInfo;
        this.openPlayerApplyService = openPlayerApplyService;
        this.playerCleanMapper = playerCleanMapper;
        this.openPlayerCleanService = openPlayerCleanService;
        this.currentNum = currentNum;
    }

    @Override
    public void run() {
        try {
            OpenPlayerEntity item = this.data;
            String applyCode = item.getApplyCode();
            if (this.applyInfo != null) {
                OpenPlayerClean playerClean = this.openPlayerCleanService
                    .getPlayerCleanByPlayerCode(item.getPlayerCode());
                if (playerClean == null) {
                    playerClean = new OpenPlayerClean();
                }
                playerClean.setAge(this.openPlayerCleanService.getAge(item, applyInfo));
                playerClean.setIsAdult(this.openPlayerCleanService.isAdult(item, applyInfo));
                playerClean.setSex(this.openPlayerCleanService.getSex(item, applyInfo));
                playerClean.setApplyCode(applyCode);
                playerClean.setApplyTime(applyInfo.getApplyTime());
                playerClean.setGmtCreate(new Date());
                playerClean.setPlayerBirth(item.getPlayerBirth());
                playerClean.setPlayerCerNo(item.getPlayerCerNo());
                playerClean.setPlayerCerType(item.getPlayerCerType());
                playerClean.setPlayerCode(item.getPlayerCode());
                playerClean.setPlayerName(item.getPlayerName());
                if (StringUtils.isBlank(item.getTerminal())) {
                    playerClean.setTerminal("dmp");
                } else {
                    playerClean.setTerminal(item.getTerminal());
                }
                //插入清洗数据
                this.openPlayerCleanService.insertOrUpdate(playerClean);
            }
            //修改清洗状态
            this.openPlayerApplyService.updatePlayerCleanStatus(item);
        } catch (Exception e) {
            log.error("", e);
            // TODO: handle exception
        }

    }
}
