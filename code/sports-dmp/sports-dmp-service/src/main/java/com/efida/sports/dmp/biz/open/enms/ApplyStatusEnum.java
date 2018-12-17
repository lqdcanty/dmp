/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.enms;

/**
 * 
 * @author zoutao
 * @version $Id: ApplyStatusEnum.java, v 0.1 2018年5月24日 下午7:27:14 zoutao Exp $
 */
public enum ApplyStatusEnum {
                             /**
                              * 待支付
                              */
                             WAIT_PAY("wait_pay", "待支付"),

                             /**处理中
                              */
                             WAIT_COMPLETE("wait_complete", "待完善"),
                             /**
                              * 成功
                              */
                             SUCCESS("success", "成功"),
                             /**
                              * 失败
                              */
                             FAIL("fail", "失败"),
                             /**
                              * 废弃
                              */
                             ABANDONED("abandoned", "废弃");

    private String code;
    private String cname;

    private ApplyStatusEnum(String code, String cname) {
        this.code = code;
        this.cname = cname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

}
