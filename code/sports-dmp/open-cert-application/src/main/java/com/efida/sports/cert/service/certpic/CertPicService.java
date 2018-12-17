/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.service.certpic;

import java.awt.FontFormatException;
import java.io.IOException;

import com.efida.sports.cert.model.CerpicInfoModel;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: CertPicService.java, v 0.1 2018年8月5日 下午8:25:02 wang yi Exp $
 */
public interface CertPicService {

    /**
     * 生成一张图片
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param certModel
     * @return
     * @throws IOException 
     * @throws FontFormatException 
     */
    public byte[] generateCertPic(CerpicInfoModel certModel) throws FontFormatException,
                                                             IOException;

    /**
     * 
     * 生成图片上传fastDfs
     * @param certModel
     * @return
     */
    public String generateCertPicFastDfs(CerpicInfoModel certModel);

}
