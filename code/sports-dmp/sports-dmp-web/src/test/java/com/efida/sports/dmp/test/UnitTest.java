package com.efida.sports.dmp.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sport.dmp.facade.OpenUnitFacade;
import com.efida.sport.dmp.facade.model.UnitAccountDto;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sport.dmp.facade.result.DmpPageResult;
import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.dao.entity.PlayerStatisticalAnalysisModel;
import com.efida.sports.dmp.service.player.OpenPlayerCleanService;

/**
 * 
 * 合作伙伴测试
 * @author wang yi
 * @desc 
 * @version $Id: UnitTest.java, v 0.1 2018年7月13日 下午4:37:22 wang yi Exp $
 */
public class UnitTest extends BaseTest {

    @Reference
    private OpenUnitFacade         unitFacade;

    @Autowired
    private OpenPlayerCleanService cleanService;

    @Test
    public void getUnitPages() {
        DefaultResult<DmpPageResult<UnitAccountDto>> unitAccounts = unitFacade.getUnitAccounts("智能",
            1, 10);
        System.err.println(JSON.toJSONString(unitAccounts));
    }

    @Test
    public void cleanOpenPlayer() {
        cleanService.cleanOpenPlayer();
    }

    @Test
    public void getSexStatistics() {
        PlayerStatisticalAnalysisModel sexStatistics = cleanService.getSexStatistics();
        System.err.println("+++++" + JSON.toJSONString(sexStatistics));

        PlayerStatisticalAnalysisModel adultStatistics = cleanService.getAdultStatistics();
        System.err.println("+++++" + JSON.toJSONString(adultStatistics));

        System.out.println();
    }

}