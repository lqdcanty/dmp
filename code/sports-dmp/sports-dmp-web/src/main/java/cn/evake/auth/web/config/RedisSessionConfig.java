package cn.evake.auth.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * 
 * 分布式session
 * 存留时间3小时
 * @author wang yi
 * @desc 
 * @version $Id: RedisSessionConfig.java, v 0.1 2018年7月3日 下午2:22:55 wang yi Exp $
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 3, redisNamespace = "ekJsession")
public class RedisSessionConfig {

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new CookieHttpSessionStrategy();
    }

}