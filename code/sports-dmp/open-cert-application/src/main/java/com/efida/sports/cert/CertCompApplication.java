package com.efida.sports.cert;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: CertCompApplication.java, v 0.1 2018年6月15日 上午11:08:09 wang yi Exp $
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = { "com.efida.sports" })
@MapperScan(basePackages = { "com.efida.sports.cert.dao.mapper" })
public class CertCompApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertCompApplication.class, args);
    }

}
