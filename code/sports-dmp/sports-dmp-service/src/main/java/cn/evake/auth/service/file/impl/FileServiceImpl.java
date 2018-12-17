package cn.evake.auth.service.file.impl;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import cn.evake.auth.service.file.FileService;
import cn.evake.auth.util.ServletUtil;

@Service
@ConfigurationProperties(prefix = "web")
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String staticPath;

    private String uploadPath;

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public String ceateFileToLocalDir(String fileName, byte[] bytes) {
        File file = new File(staticPath + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        logger.info("File save success ! realPath :{}", file.getPath());
        return file.getPath();
    }

    /**
    * 
    * 上传文件到文件系统
    * @param fileFullName
    * @param file
    * @param request
    */

    @Override
    public String upLoadFileToFileSys(String fileFullName, byte[] file, HttpServletRequest request) {
        String fileOriginalName = fileFullName;
        String domain = ServletUtil.getCtx(request) + "/"
                        + StringUtils.remove(staticPath, uploadPath);
        byte[] fileByte = file;
        //第一次上传该文件 实现本地写入文件
        String fileRealpath = ceateFileToLocalDir(fileFullName, fileByte);
        String url = domain + fileFullName;
        //保存文件信息
        logger.info("crate fileName : {} to server success! url is : {}", fileOriginalName, url);
        return url;
    }

}
