package com.efida.sports.dmp.service.msg.impl.cms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.exception.ServiceException;
import com.efida.sports.dmp.service.msg.IMsgService;
import com.efida.sports.dmp.service.msg.conf.SmsConfig;

import cn.evake.auth.service.common.CacheService;
import cn.evake.auth.util.HttpUtils;
import cn.evake.auth.util.RegexUtils;

@Service
public class MsgServiceImpl implements IMsgService {

    @Autowired
    private SmsConfig     smsConfig;

    @Autowired
    private CacheService  cacheService;

    private final String  UCENTER_VERIFY_CODE_STATUS_KEY = "dmp_Msg_lock_";

    private final String  appDownLoad                    = "http://wx.zntyydh.com/static/down_app";

    private static Logger log                            = LoggerFactory
        .getLogger(MsgServiceImpl.class);

    @Override
    public boolean sendImMsg(String mobile, String msg) {
        if (!RegexUtils.checkMobile(mobile)) {
            throw new DmpBusException("发送信息非正确的手机号码");
        }
        //判断发送状态，避免短时间重复发送
        String sendCodeStatus = cacheService.get(UCENTER_VERIFY_CODE_STATUS_KEY + mobile);
        if (StringUtils.isNotEmpty(sendCodeStatus)) {
            throw new ServiceException("同手机号,一分钟不允许重复发送");
        }
        String msgContent = String.format(msg);
        String urlStr = "http://sms.106818.com:9885/c123/sendsms";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", smsConfig.uid);
        params.put("pwd", smsConfig.pwd);
        params.put("mobile", mobile);
        params.put("content", msgContent);
        String code = HttpUtils.executeGBKGet(urlStr, params);
        log.info("发送短信手机号:{}返回状态：{}", mobile, code);
        cacheService.put(UCENTER_VERIFY_CODE_STATUS_KEY + mobile, code, 1 * 60);
        return "100".equals(code);
    }

    @Override
    public boolean sendDownMsg(String mobile) {
        String msg = "【大赛组委会】恭喜您顺利完成比赛！您可前往智体运动app领取由大赛组委会颁发的完赛证书和奖章，纪念自己的国家级赛事经历，见证智能体育的新时代！"
                     + appDownLoad;
        return sendImMsg(mobile, msg);
    }

}
