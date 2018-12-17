/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.file.service.FileUploadApiService;
import com.efida.file.vo.FileTransmissionVo;
import com.efida.sports.dmp.service.dubbo.intergration.FileUploadFacadeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author antony
 * @version $Id: FileUploadFacadeClient.java, v 0.1 2018年7月21日 下午6:16:50 antony Exp $
 */
@Service("fileUploadFacadeClient")
public class FileUploadFacadeClientImpl implements FileUploadFacadeClient {

    @Reference
    private FileUploadApiService fileUploadApiService;

    private static Logger logger = LoggerFactory.getLogger(FileUploadFacadeClientImpl.class);


    @Override
    public String uploadSourceFile(FileTransmissionVo vo) {
        return fileUploadApiService.uploadSourceFile(vo);
    }
}
