package com.efida.sports.dmp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: DmpStartApplication.java, v 0.1 2018年6月15日 上午11:08:09 wang yi Exp $
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = { "com.efida.sports", "cn.evake" })
@MapperScan(basePackages = { "com.efida.sports.dmp.dao.mapper", "cn.evake.auth.dao.mapper",
                             "cn.evake.app.dao.mapper" })
public class DmpStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmpStartApplication.class, args);
    }

}
