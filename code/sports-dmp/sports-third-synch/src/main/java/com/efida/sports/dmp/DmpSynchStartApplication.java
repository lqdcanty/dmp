package com.efida.sports.dmp;

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
 * @version $Id: DmpStartApplication.java, v 0.1 2018年6月15日 上午11:08:09 wang yi Exp $
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = { "com.efida.sports.dmp" })
public class DmpSynchStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmpSynchStartApplication.class, args);
    }

}
