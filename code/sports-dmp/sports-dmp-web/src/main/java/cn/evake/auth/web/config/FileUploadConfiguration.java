package cn.evake.auth.web.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Springboot 文件上传配置
 * 
 * @author wang yi
 * @desc 
 * @version $Id: FileUploadConfiguration.java, v 0.1 2017年11月9日 下午5:13:21 wang yi Exp $
 */
@Configuration
@ConfigurationProperties(prefix = "web")
public class FileUploadConfiguration {

    private Logger  log            = LoggerFactory.getLogger(this.getClass());

    private String  uploadPath;

    private String  staticPath;

    private String  maxUpload;

    private boolean isCheckFileDir = false;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(maxUpload); //单文件最大1000M
        // 配置文件上传路径第一次检查目录
        File file = new File(uploadPath);
        if (isCheckFileDir == false && !file.exists()) {
            file.mkdirs();
            log.warn("fileupload is not exist, create new path :{}", uploadPath);
        }
        //静态资源文件路径
        File filedir = new File(staticPath);
        if (isCheckFileDir == false && !filedir.exists()) {
            filedir.mkdirs();
            log.warn("staticfilePath is not exist, create new path :{}", staticPath);
        }
        factory.setLocation(uploadPath);
        log.info("fileupload is checked file upload path :{}", uploadPath);
        return factory.createMultipartConfig();
    }

    //////////////////////////////////////////////////////黄金分割线////////////////////////////////////////////////////////////////
    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public String getMaxUpload() {
        return maxUpload;
    }

    public void setMaxUpload(String maxUpload) {
        this.maxUpload = maxUpload;
    }

    public boolean isCheckFileDir() {
        return isCheckFileDir;
    }

    public void setCheckFileDir(boolean isCheckFileDir) {
        this.isCheckFileDir = isCheckFileDir;
    }

}