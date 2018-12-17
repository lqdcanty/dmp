/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.document;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.api.vo.AttachmentVo;
import cn.evake.auth.api.vo.DocumentVo;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.DiskUserVo;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 用戶信息操作controller
 * @author Evance
 * @version $Id: UserApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "api/document", produces = "application/json")
@Api(value = "文档管理接口", tags = "系统文档管理")
@Authority(value = AuthorityTypeEnum.NoValidate)
public class DocumentApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ApiOperation(value = "文档信息获取", notes = "用于查詢文档信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "uuid", value = "文档唯一标识", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "get", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DocumentVo> getMeUserInfo(String uuid, HttpServletRequest request) {
        ResultWrapper<DocumentVo> resultWrapper = new ResultWrapper<DocumentVo>();
        try {
        } catch (Exception e) {
            logger.error("文档信息获取失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "文档列表", notes = "用于文档列表查询")
    @ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字(暂不支持)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "size", value = "数量", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<DocumentVo>> getDocuments(@RequestParam(name = "keyword", required = false) String keyword,
                                                                @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                                HttpServletRequest request) {
        ResultWrapper<PagingResult<DocumentVo>> resultWrapper = new ResultWrapper<PagingResult<DocumentVo>>();
        try {
            ArrayList<DocumentVo> documents = new ArrayList<DocumentVo>();
            ArrayList<AttachmentVo> arrayList = new ArrayList<AttachmentVo>();
            AttachmentVo attachmentVo = new AttachmentVo();
            attachmentVo.setFileName("SDK包1.0");
            attachmentVo.setFileSize("3.4M");
            attachmentVo.setFileUrl(
                "http://f2.market.xiaomi.com/download/AppStore/059037412af5b4c4707771477d93f2931aa3571eb/com.yuedong.sport.apk");
            AttachmentVo attachmentVo2 = new AttachmentVo();
            attachmentVo2.setFileName("文档");
            attachmentVo2.setFileSize("3.4M");
            attachmentVo2.setFileUrl(
                "http://f2.market.xiaomi.com/download/AppStore/059037412af5b4c4707771477d93f2931aa3571eb/com.yuedong.sport.apk");
            arrayList.add(attachmentVo);
            arrayList.add(attachmentVo2);
            DocumentVo documentVo = new DocumentVo();
            documentVo.setType(1);
            documentVo.setName("开放接口最新版");
            documentVo.setIntro("这个一个简介");
            documentVo.setLastUname("王小二");
            documentVo.setPublishTimeStr("2018-07-26");
            documentVo.setVersion("1.0.2");
            documentVo.setAttachment(arrayList);
            documents.add(documentVo);
            resultWrapper.setData(new PagingResult<>(documents, 1, pageNumber, pageSize));
        } catch (Exception e) {
            logger.error("查询失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "文档添加", notes = "用于文档添加")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DocumentVo> addDocument(@ModelAttribute DocumentVo decument,
                                                 HttpServletRequest request) {
        ResultWrapper<DocumentVo> resultWrapper = new ResultWrapper<DocumentVo>();
        try {
        } catch (Exception e) {
            logger.error("文档创建失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "文档修改", notes = "用于文档修改")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "update", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DiskUserVo> updateDocument(@ModelAttribute DocumentVo decument,
                                                    HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
        } catch (Exception e) {
            logger.error("文档修改失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

}
