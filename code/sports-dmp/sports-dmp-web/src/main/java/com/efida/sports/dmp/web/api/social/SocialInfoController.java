package com.efida.sports.dmp.web.api.social;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.web.vo.ResultWrapper;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sport.facade.model.SocialInfoCountModel;
import com.efida.sport.facade.model.SocialInfoModel;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.dubbo.intergration.SocialInfoFacadeClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "dmp/api/moSocialInfo", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class SocialInfoController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SocialInfoFacadeClient socialInfoFacadeClient;

    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "info/list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<SocialInfoModel> getInfoList(@RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) String type,
                                                            @RequestParam(required = false, defaultValue = "1") String page,
                                                            @RequestParam(required = false, defaultValue = "20") String limit,
                                                            HttpServletRequest request) {
        LayerPagevo<SocialInfoModel> resultWrapper = new LayerPagevo<SocialInfoModel>();
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("currentPage", page);
            params.put("pageSize", limit);
            params.put("type",type);
            params.put("keyword",keyword);
            PagingResult<SocialInfoModel> resultModels = null;
            resultModels = socialInfoFacadeClient.findPage(params);
             if (type.equals("stick")){
                resultModels = socialInfoFacadeClient.findPageByStick(params);
            }
            resultWrapper.setData(resultModels.getList());
            resultWrapper.setCount(resultModels.getAllRow());
        } catch (DmpBusException ex) {
            resultWrapper.setMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setMsg("系统错误");
        }
        return resultWrapper;
    }
    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "updateStatus", method = { RequestMethod.POST })
    @ResponseBody  //加/取消精品
    public ResultWrapper<SocialInfoModel> updateStatus(@RequestParam(required = true) String status,
                                                              @RequestParam(required = true) String id,
                                                              HttpServletRequest request) {
        ResultWrapper<SocialInfoModel> resultWrapper = new ResultWrapper<SocialInfoModel>();
        try {
            SocialInfoModel bean =new SocialInfoModel();
            bean.setId(id);
            bean.setSplendidStatic("0");
            if(status.equals("0"))
                bean.setSplendidStatic("1");
            socialInfoFacadeClient.updateStatus(bean);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }
    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "updateStick", method = { RequestMethod.POST })
    @ResponseBody  //加/取消置顶
    public ResultWrapper<SocialInfoModel> updateStick( @RequestParam(required = true) String id,
                                                       @RequestParam(required = true) String status,
                                                              HttpServletRequest request) {
        ResultWrapper<SocialInfoModel> resultWrapper = new ResultWrapper<SocialInfoModel>();
        try {
            if(status.equals("0")){
                socialInfoFacadeClient.saveStickInfo(id);
            }else {
                socialInfoFacadeClient.removeStickInfo(id);
            }

        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }
    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "info", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<SocialInfoModel> getInfo( @RequestParam(required = true) String id,
                                                   @RequestParam(required = true) String type,
                                                               HttpServletRequest request) {
        ResultWrapper<SocialInfoModel> resultWrapper = new ResultWrapper<SocialInfoModel>();
        try {
            SocialInfoModel bean = null;
            if (type.equals("stick")){
                bean = socialInfoFacadeClient.getSocialStick(id);
            }else {
                bean = socialInfoFacadeClient.getSocialInfo(id);
            }

            resultWrapper.setData(bean);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }
    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "count", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SocialInfoCountModel> getCount(@RequestParam(required = false) String keyword, HttpServletRequest request) {
        ResultWrapper<SocialInfoCountModel> resultWrapper = new ResultWrapper<SocialInfoCountModel>();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("keyword",keyword);
            SocialInfoCountModel bean = socialInfoFacadeClient.getInfoCount(params);
            resultWrapper.setData(bean);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }


    //@Authority(value = AuthorityTypeEnum.NoAuthority)
    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "sort", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<SocialInfoCountModel> sort(
            @RequestParam(required = true) String id,
            @RequestParam(required = true) String sort,
            HttpServletRequest request) {
        ResultWrapper<SocialInfoCountModel> resultWrapper = new ResultWrapper<SocialInfoCountModel>();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id);
            params.put("stickSort", sort);
            socialInfoFacadeClient.stickSort(params);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODEwMTIxMTE2MTg=")
    @RequestMapping(value = "auth", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<SocialInfoCountModel> auth(
            @RequestParam(required = true) String id,
            @RequestParam(required = true) String authStutas,
            HttpServletRequest request) {
        ResultWrapper<SocialInfoCountModel> resultWrapper = new ResultWrapper<SocialInfoCountModel>();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id);
            params.put("authStutas", authStutas);
            socialInfoFacadeClient.infoAuthCheck(params);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }
}
