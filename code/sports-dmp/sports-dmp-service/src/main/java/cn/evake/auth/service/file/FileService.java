package cn.evake.auth.service.file;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件服务
 * @author wangyi
 * @desc 
 * @version $Id: CommonUploadService.java, v 0.1 2017年12月25日 上午11:03:45 wangyi Exp $
 */
public interface FileService {
    /**
    * @title 保存文件到本地目录
    * @methodName
    * @author wangyi
    * @description
    * @param fileName 文件名
    * @param bytes 数据
    * @return 本地存储路径
    */

    String ceateFileToLocalDir(String fileName, byte[] bytes);

    /**
      * 
      * 上传文件到文件系统
      * @param fileFullName
      * @param file
      * @param request
      * @return 上传文件的信息
      */

    String upLoadFileToFileSys(String fileFullName, byte[] file, HttpServletRequest request);

}
