package com.efida.sports.cert;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 生成代码
 * </p>
 * @date 2017/12/18
 */
public class GeneratorServiceEntity {

    @Test
    public void generateCode() {
        String packageName = "com.efida.sports.dmp.dao";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        //generateByTables(serviceNameStartWithI, packageName, "sys_user_role", "sys_user_info");
        generateByTables(serviceNameStartWithI, packageName);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName,
                                  String... tableNames) {
        String dbUrl = "jdbc:mysql://192.168.8.4:3306/sports-dmp?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false);
        config.setAuthor("wang yi");
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
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("4ladGAjWGkk7Q");
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
