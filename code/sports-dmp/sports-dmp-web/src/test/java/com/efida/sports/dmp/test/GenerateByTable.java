package com.efida.sports.dmp.test;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GenerateByTable {

    @Test
    public void generateCode() {
        String packageName = "com.efida.sports.dmp.synch.data.jiubiao.dao";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "syn_relation");
        generateByTables(serviceNameStartWithI, packageName);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName,
                                  String... tableNames) {
        String dbUrl = "jdbc:mysql://60.205.222.39:3306/shot?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false);
        config.setAuthor("wangyi");
        config.setOutputDir("d:\\codeGen");
        config.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        config.setEnableCache(false);// XML 二级缓存
        config.setBaseResultMap(true);// XML ResultMap
        config.setFileOverride(true);//文件覆蓋
        config.setBaseColumnList(true);// XML columList
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUrl(dbUrl);
        dataSourceConfig.setUsername("dbreader");
        dataSourceConfig.setPassword("Devuserdata_2018");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true);
        strategyConfig.setEntityLombokModel(false);
        strategyConfig.setDbColumnUnderline(true);
        if (tableNames == null || tableNames.length < 1) {
            strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        } else {
            strategyConfig.setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        }
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName);
        packageConfig.setController("controller");
        packageConfig.setEntity("entity");
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.execute();
    }

    /**
     * 生成表单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param packageName
     * @param tableNames
     */
    @SuppressWarnings("unused")
    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }

}
