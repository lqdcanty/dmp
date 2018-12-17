/**
 * 
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package cn.evake.auth.api.file;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.file.FileService;
import cn.evake.auth.web.vo.ResultWrapper;
import cn.evake.auth.web.vo.UeditorResult;

/**
 * 
 * 文件管理API
 * @author wang yi
 * @desc 
 * @version $Id: FileController.java, v 0.1 2017年12月27日 下午5:56:34 wang yi Exp $
 */
@RestController("fileController")
@Api(value = "文件/文档API", tags = "系统文件上传")
@RequestMapping(value = "api/file", produces = "application/json")
public class FileApiController {

    private Logger      log = LoggerFactory.getLogger(FileApiController.class);

    @Autowired
    private FileService fileManager;

    /**
      * 
      * 文件上传
      * @param request
      * @param file
      * @return
      */

    @ApiOperation(value = "文件上传", notes = "上传文件到文件系统", httpMethod = "POST")
    @PostMapping("upload")
    public ResultWrapper<String> upload(HttpServletRequest request,
                                        @RequestParam("file") MultipartFile file) {
        ResultWrapper<String> result = new ResultWrapper<>();
        String fileFullName = file.getOriginalFilename();
        try {
            byte[] fileBytes = file.getBytes();
            String fileUrl = fileManager.upLoadFileToFileSys(fileFullName, fileBytes, request);
            result.setData(fileUrl);
        } catch (Exception e) {
            result.setErrorMsg(e.getMessage());
            log.error("upload fileName : {} to server fail!", fileFullName, e);
        }
        return result;
    }

    @ApiOperation(value = "文件上传", notes = "上传文件到文件系统", httpMethod = "POST")
    @PostMapping("ueditor/upload")
    public UeditorResult uditorUpload(HttpServletRequest request,
                                      @RequestParam("file") MultipartFile file) {
        UeditorResult result = new UeditorResult();
        String fileFullName = file.getOriginalFilename();
        try {
            byte[] fileBytes = file.getBytes();
            String fileUrl = fileManager.upLoadFileToFileSys(fileFullName, fileBytes, request);
            result.setOriginal(fileFullName);
            result.setTitle(fileFullName);
            result.setUrl(fileUrl);
        } catch (AuthBusException e) {
            result.setErrMessage(e.getMessage());
            log.error("upload fileName : {} to server fail!", fileFullName, e);
        } catch (Exception e) {
            result.setErrMessage("上传失败");
            log.error("upload fileName : {} to server fail!", fileFullName, e);
        }
        return result;
    }

}
