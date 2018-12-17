/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.file.service;

import cn.evake.file.model.FileResWrapper;
import cn.evake.file.model.FileTransferModel;

/**
 * 文件对象工厂
 * @author wang yi
 * @version $Id: FileFactory.java, v 0.1 2018年7月22日 下午5:10:03 wang yi Exp $
 */
public interface FileFactoryService {

    /**
     * 上传文件
     * @param fileModel
     * @return
     */
    public FileResWrapper upSourceFile(FileTransferModel fileModel);

    /**
     * 上传临时文件
     * 临时文件无URL
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param fileModel 文件对象
     * @param expirationTime 单位分钟
     * @return  文件唯一标识
     */
    public FileResWrapper upTempSourceFile(FileTransferModel fileModel, int expirationTime);

    /**
     * 获取临时文件
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param fileUid
     * @return
     */
    public FileTransferModel getTempSourceFile(String fileUid);

}
