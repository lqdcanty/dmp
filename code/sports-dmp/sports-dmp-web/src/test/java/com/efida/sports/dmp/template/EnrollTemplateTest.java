package com.efida.sports.dmp.template;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sport.dmp.facade.OpenUnitFacade;
import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.template.EnrollInfoTemplate;

import cn.evake.excel.util.ExcelUtil;

/**
 * 
 * 合作伙伴测试
 * @author wang yi
 * @desc 
 * @version $Id: UnitTest.java, v 0.1 2018年7月13日 下午4:37:22 wang yi Exp $
 */
public class EnrollTemplateTest extends BaseTest {

    @Reference
    private OpenUnitFacade         unitFacade;

    @Autowired
    private OpenPlayerApplyService playerService;

    @Test
    public void importTemplate() {
        List<Map<String, Object>> readXlsxWithSheet = ExcelUtil.readXlsxWithSheet(
            new File("D:\\zntyTemplate\\运动员报名（个人赛报名）-模板-20180724.xlsx"), "open_enroll_info");
        System.err.println(JSON.toJSONString(readXlsxWithSheet));
        Map<String, Object> map = readXlsxWithSheet.get(0);
        List<EnrollInfoTemplate> enrollinfoVos = playerService
            .parseEnrollInfoExcelVos(readXlsxWithSheet);
        System.err.println(JSON.toJSONString(enrollinfoVos));
    }

}