package com.efida.sports.dmp.service.msg;

/**
 * 短信服务
 * @author wang yi
 * @desc 
 * @version $Id: IMsgService.java, v 0.1 2018年10月26日 下午2:26:35 wang yi Exp $
 */
public interface IMsgService {

    /**
     * 发送短信
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param mobile
     *@param msg 消息内容
     * @return
     */
    boolean sendImMsg(String mobile, String msg);

    /**
     * 发送官方信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param mobile
     * @return
     */
    boolean sendDownMsg(String mobile);

}
