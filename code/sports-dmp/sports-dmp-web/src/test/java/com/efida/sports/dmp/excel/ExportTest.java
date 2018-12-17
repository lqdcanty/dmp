package com.efida.sports.dmp.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.efida.sports.dmp.web.api.PlayerApiController;

import cn.evake.excel.handel.ExcelHanlel;

/** 
 * 测试导出Excel数据 
 *  
 * @author jiaxingHuang 
 * @version 1.0 2014年8月20日 
 *  
 */
public class ExportTest {

    @Test
    public void test() throws Exception {
        List<TestModel> list = new ArrayList<TestModel>();
        for (int i = 0; i < 25; i++) {
            TestModel testModel = new TestModel();
            testModel.setId(Long.valueOf(i + 1));
            testModel.setName("黄佳兴" + (i + 1));
            testModel.setAge(Random.class.newInstance().nextInt(100));
            testModel.setBirthday(new Date());
            testModel.setMark("黄佳兴test" + Random.class.newInstance().nextInt(100));
            list.add(testModel);
        }
        FileOutputStream out = new FileOutputStream("D:\\test.xls");
        ExcelHanlel<TestModel> util1 = new ExcelHanlel<TestModel>(TestModel.class);
        util1.getListToExcel(list, "test信息", out);
        System.out.println("----执行完毕----");
    }

    @SuppressWarnings("resource")
    @Test
    public void exportTemplate() throws Exception {
        List<TestModel> list = new ArrayList<TestModel>();
        for (int i = 0; i < 25; i++) {
            TestModel testModel = new TestModel();
            testModel.setId(Long.valueOf(i + 1));
            testModel.setName("黄佳兴" + (i + 1));
            testModel.setAge(Random.class.newInstance().nextInt(100));
            testModel.setBirthday(new Date());
            testModel.setMark("黄佳兴test" + Random.class.newInstance().nextInt(100));
            list.add(testModel);
        }
        byte[] templateFile = IOUtils.toByteArray(
            PlayerApiController.class.getResourceAsStream("/excle/运动员报名-个人赛报名-模板.xlsx"));
        ExcelHanlel<TestModel> util1 = new ExcelHanlel<TestModel>(TestModel.class);
        byte[] templateToExcel = util1.generTemplateExcel(list, "open_enroll_info", 3,
            templateFile);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D://g.xlsx"));
        fileOutputStream.write(templateToExcel);
        System.out.println("----执行完毕----");
    }

}