/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.img;

import java.awt.FontFormatException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efida.sports.cert.base.BaseTest;
import com.efida.sports.cert.model.CerpicInfoModel;
import com.efida.sports.cert.service.certpic.CertPicService;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: PicTest.java, v 0.1 2018年8月8日 上午11:14:27 wang yi Exp $
 */
public class PicTest extends BaseTest {

    @Autowired
    private CertPicService certPicService;

    private Logger         logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void gennerPicCert() throws FontFormatException, IOException {
        String localFolder = "D:\\temp\\";
        logger.info("开始生成证书......");
        CerpicInfoModel cerpicInfoModel = new CerpicInfoModel();
        cerpicInfoModel.setCertMatchName("智能跑步赛事");
        cerpicInfoModel.setCertEventName("线上3公里跑");
        cerpicInfoModel.setCertName("李智能");
        cerpicInfoModel.setCertSn("20180184");
        cerpicInfoModel.setScoreDesc("205分");
        cerpicInfoModel.setCertTime(new Date());
        cerpicInfoModel.setPartTime(new Date());
        byte[] certPic = certPicService.generateCertPic(cerpicInfoModel);
        String fileName = new Date().getTime() + ".jpg";
        FileOutputStream fileOutputStream = new FileOutputStream(localFolder + fileName);
        IOUtils.write(certPic, fileOutputStream);
        logger.info("生成证书完成...储存在:{}", localFolder + fileName);
        Runtime.getRuntime().exec("explorer " + localFolder);
    }

}
