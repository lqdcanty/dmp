/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.file.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.evake.auth.service.common.CacheService;
import cn.evake.auth.util.MD5Utils;
import cn.evake.file.enmus.FileServerEnum;
import cn.evake.file.model.FileResWrapper;
import cn.evake.file.model.FileTransferModel;
import cn.evake.file.service.FileFactoryService;

/**
 * 
 * @author Evance
 * @version $Id: FileFactoryImpl.java, v 0.1 2018年7月22日 下午5:11:18 Evance Exp $
 */
@Service
public class FileFactoryIServciempl implements FileFactoryService {

    private Logger       logger     = LoggerFactory.getLogger(this.getClass());
    /**
     *文件服务器类型
     */
    private String       fileServer = "localserver";

    @Autowired
    private CacheService cacheService;

    /**
     * 初始化文件服务器类型
     * 并检查服务配置是否正确
     */
    @PostConstruct
    public void intFileFactoryConfig() {
        if (StringUtils.isBlank(fileServer)) {
            throw new RuntimeException("文件文件服务未配置");
        }
        FileServerEnum enumByCode = FileServerEnum.getEnumByCode(fileServer);
        //未通过文件服务检查
        if (enumByCode == null) {
            throw new RuntimeException("暂时不支持:" + fileServer + " 文件上传服务器");
        }
    }

    @Override
    public FileResWrapper upSourceFile(FileTransferModel fileModel) {
        throw new RuntimeException("暂未开发");
    }

    @Override
    public FileResWrapper upTempSourceFile(FileTransferModel fileModel, int expirationTime) {
        if (StringUtils.isBlank(fileModel.getUid())) {
            throw new RuntimeException("临时文件用户UID不能为空");
        }
        String fileUid = fileModel.getUid() + MD5Utils.getFileMD5(fileModel.getBtyes());
        boolean fla = cacheService.putObj(fileUid, fileModel, expirationTime);
        if (!fla) {
            throw new RuntimeException("临时文件存储失败");
        }
        return new FileResWrapper(true, null, fileUid, null);
    }

    @Override
    public FileTransferModel getTempSourceFile(String fileUid) {
        FileTransferModel fileModel = (FileTransferModel) cacheService.getObj(fileUid);
        return fileModel;
    }

}
