package com.efida.sports.dmp.synch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据源Bean配置
 * @author wang yi
 * @desc 
 * @version $Id: DataSourceConfig.java, v 0.1 2018年9月4日 下午2:29:27 wang yi Exp $
 */
@Configuration
public class DataSourceConfig {

    ///智能体育
    @Bean(name = "zntyDataSource")
    @Qualifier("zntyDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.znty")
    public DataSource zntyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "zntyJdbc")
    public JdbcTemplate zntyJdbcTemplate(@Qualifier("zntyDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    //智能定向
    @Bean(name = "smartrunDataSource")
    @Qualifier("smartrunDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.smartrun")
    public DataSource smartrunDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "smartrunJdbc")
    public JdbcTemplate smartrunJdbcTemplate(@Qualifier("smartrunDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    //智能定向
    @Bean(name = "jiubiaoDataSource")
    @Qualifier("jiubiaoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.jiubiao")
    public DataSource jiubiaoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jiubiaoJdbc")
    public JdbcTemplate jiubiaoTemplate(@Qualifier("jiubiaoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}