package cn.evake.auth.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 网站配置Adapter
 * @author Evance
 * @version $Id: WebConfig  .java, v 0.1 2018年3月10日 下午7:09:08 Evance Exp $
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    /** 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 权限拦截器
         */
        registry.addInterceptor(authIntercepter()).addPathPatterns("/**")
            .excludePathPatterns("/assets/**");
    }

    /**
     * 权限拦截器
     * @return 
     */
    @Bean
    public AuthorityAnnotationInterceptor authIntercepter() {
        return new AuthorityAnnotationInterceptor();
    }

}