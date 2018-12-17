package com.efida.sports.dmp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Springboot 加载到参数后自动加载该配置
 * @author wang yi
 * @desc 
 * @version $Id: SwaggerConfig.java, v 0.1 2018年6月15日 下午3:19:07 wang yi Exp $
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(value = "${spring.profiles.active}")
    private String springProfiles;

    @Bean
    public Docket createRestApi() {
        if (springProfiles.equals("dev")) {
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .paths(PathSelectors.ant("/**")).build();//URL过滤
        } else {
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.efida.sports.dmp.open.controller"))
                .build();
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("首届全国智能体育运动会数据交换平台接口规范")
            .description(
                "数据交换平台适用于“智体运动会”，利用该平台可以建立自上而下的数据交换体系，统一数据交换标准，统一交换渠道，实现数据中心级联和数据采集。"
                        + "数据交换平台由管理控制层与传输层组成，管理控制层提供平台管理、接收管理、发送管理等管理功能和数据接收服务、数据发送服务、数据抽取服务等一系列服务功能；传输层提供基于HTTP协议的传输通道。")
            .termsOfServiceUrl("http://www.efida.com.cn/")
            .contact(new Contact("王依", "", "wang765aaa@163.com")).version("1.0").build();
    }
}