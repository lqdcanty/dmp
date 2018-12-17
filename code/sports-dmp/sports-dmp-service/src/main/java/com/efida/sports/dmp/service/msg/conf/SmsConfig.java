package com.efida.sports.dmp.service.msg.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Value("${sms.uid}")
    public String uid;
    @Value("${sms.pwd}")
    public String pwd;

}
