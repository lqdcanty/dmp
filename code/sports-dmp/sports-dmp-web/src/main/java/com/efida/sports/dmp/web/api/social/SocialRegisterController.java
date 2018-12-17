package com.efida.sports.dmp.web.api.social;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.web.vo.ResultWrapper;
import com.efida.sport.facade.model.MoRegisterModel;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.dubbo.intergration.SocialRegisterFacadeClient;
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
@RequestMapping(value = "dmp/api/moRegisterManage", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class SocialRegisterController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SocialRegisterFacadeClient socialRegisterFacadeClient;

    @Authority(permissionCode = "MjAxODEwMDkxNzI2MTc=")
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<MoRegisterModel> getSettlementReport(@RequestParam(required = false) String keyword,
                                                            @RequestParam(required = false, defaultValue = "1") String page,
                                                            @RequestParam(required = false, defaultValue = "20") String limit,
                                                            HttpServletRequest request) {
        LayerPagevo<MoRegisterModel> resultWrapper = new LayerPagevo<MoRegisterModel>();
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("currentPage", page);
            params.put("pageSize", limit);
            params.put("keyword", keyword);
            PagingResult<MoRegisterModel> resultModels = socialRegisterFacadeClient.findPage(params);
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

    @Authority(permissionCode = "MjAxODEwMDkxNzI2MTc=")
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<MoRegisterModel> getSettlementReport(@RequestParam(required = true) String bannedstatuslong,
                                                               @RequestParam(required = true) String id,
                                                                HttpServletRequest request) {
        ResultWrapper<MoRegisterModel> resultWrapper = new ResultWrapper<MoRegisterModel>();
        try {
            MoRegisterModel register =new MoRegisterModel();
            register.setId(id);
            register.setBannedstatuslong("0");
            if(bannedstatuslong.equals("0"))
                register.setBannedstatuslong("1");
            register = socialRegisterFacadeClient.updateBannedstatuslong(register);
           resultWrapper.setData(register);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.toString());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }
}
