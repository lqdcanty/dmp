/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.service.certpic.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efida.file.service.FileUploadApiService;
import com.efida.file.vo.FileTransmissionVo;
import com.efida.sports.cert.model.CerpicInfoModel;
import com.efida.sports.cert.service.certpic.CertPicService;
import com.efida.sports.cert.utils.DateUtil;
import com.efida.sports.cert.utils.ImageUtils;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: CertPicServiceImpl.java, v 0.1 2018年8月5日 下午8:25:50 wang yi Exp $
 */
@Service
public class CertPicServiceImpl implements CertPicService {

    /**
     * 日志文件
     */
    private Logger               logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 背景图
     */
    private byte[]               bkImg;

    /**
    * 章
    */
    private byte[]               chop;

    @Reference
    private FileUploadApiService fileUploadApiService;

    /**
     * 初始化读取模板到内存
     * @title
     * @methodName
     * @author wangyi
     * @throws IOException 
     * @description
     */
    @PostConstruct
    private void intReadTemplate() throws IOException {
        bkImg = IOUtils.toByteArray(
            ImageUtils.class.getResourceAsStream("/template/cert/bg_jiangzhang_v1.3.png"));
        chop = IOUtils
            .toByteArray(ImageUtils.class.getResourceAsStream("/template/cert/img_zhang_v1.3.png"));
        logger.info("int read img tempalte scuess !");
        //获取系统中可用的字体的名字  
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fn = e.getAvailableFontFamilyNames();
        for (int i = 0; i < fn.length; i++) {
            logger.info("系统支持字体--->: " + fn[i]);
        }
        if (fn == null || fn.length == 0) {
            logger.warn("系统暂无字体！请检查配置!!!!");
        }
    }

    @Override
    public byte[] generateCertPic(CerpicInfoModel certModel) throws FontFormatException,
                                                             IOException {
        //字体名称 思源黑
        String fontNameMedium = "SourceHanSansCN-Medium";
        String fontNameBold = "SourceHanSansCN-Bold";
        String fontNameNormal = "SourceHanSansCN-Normal";
        long startTime = System.currentTimeMillis();
        Font bold18f = new Font(fontNameBold, Font.BOLD, 18);
        Font medi18f = new Font(fontNameMedium, Font.PLAIN, 18);
        Font normal18f = new Font(fontNameNormal, Font.PLAIN, 18);
        byte[] newImg = null;
        //合并文字 设置日期
        newImg = ImageUtils.addTextWeatermark(bkImg,
            DateUtil.format(certModel.getPartTime(), DateUtil.NORMAL_FORMAT), normal18f,
            Color.black, 500, 875, 0);
        //合并文字 设置名称
        String certName = certModel.getCertName();
        newImg = ImageUtils.addTextWeatermark(newImg, certName, bold18f, new Color(51, 51, 51),
            ImageUtils.getCetStartPix(240, bold18f, certName) + 200, 610, 0);
        //合并文字 设置比赛项名称
        String certMatchName = certModel.getCertMatchName();
        String certEventName = certModel.getCertEventName();
        if ((certMatchName + certEventName).length() > 20) {
            //合并文字 设置比赛名称(暂时定)
            newImg = ImageUtils.addTextWeatermark(newImg, certMatchName, medi18f,
                new Color(51, 51, 51), ImageUtils.getCetStartPix(365, medi18f, certMatchName) + 210,
                705, 0);
        } else {
            //合并文字 设置比赛项名称
            String mathchName = certMatchName + "-" + certEventName;
            newImg = ImageUtils.addTextWeatermark(newImg, mathchName, medi18f,
                new Color(51, 51, 51), ImageUtils.getCetStartPix(365, medi18f, mathchName) + 225,
                705, 0);
        }
        //合并文字 设置比赛成绩
        String cerPicScore = CerpicInfoModel.getCerPicScore(certModel);
        newImg = ImageUtils.addTextWeatermark(newImg, cerPicScore, medi18f, new Color(51, 51, 51),
            ImageUtils.getCetStartPix(365, medi18f, cerPicScore) + 210, 750, 0);
        //合并文字 设置证书编号
        newImg = ImageUtils.addTextWeatermark(newImg, "NO." + certModel.getCertSn(), normal18f,
            new Color(102, 102, 102), 100, 110, 0);
        //合并图片 奖章
        newImg = ImageUtils.addImageWeatermark(newImg, chop, 480, 775, 1f);
        long endTime = System.currentTimeMillis();
        logger.info("garange cert name :{} spends:{}", certModel.getCertName(),
            (endTime - startTime));
        return newImg;
    }

    @Override
    public String generateCertPicFastDfs(CerpicInfoModel certModel) {
        try {
            byte[] generateCertPic = generateCertPic(certModel);
            logger.info("开始执行文件上传证书人-->：" + certModel.getCertName());
            FileTransmissionVo vo = new FileTransmissionVo();
            byte[] bytes = generateCertPic;
            String name = new Date().getTime() + ".png";
            vo.setBtyes(bytes);
            vo.setFileName(name);
            vo.setAppName("sport-dmp");
            vo.setLastingType("2");
            vo.setUiserId("sport-dmp");
            vo.setFileType(name.split("\\.")[1]);
            String url = fileUploadApiService.uploadSourceFile(vo);
            JSONObject json = JSON.parseObject(url);
            logger.info("文件上传成功地址:" + url);
            return json.get("url") == null ? null : json.get("url").toString();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return null;
    }

}
