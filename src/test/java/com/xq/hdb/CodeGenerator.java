package com.xq.hdb;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    static String packageName = "com.xq.hdb"; // 当前包名
    static String author = "author"; // 作者
    static String table = "tbl_test"; // 表，用逗号隔开
    static String table2 = "tbl_test";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setFileOverride(true);//覆盖之前的，如果service层或者到层已经自己写了代码，慎用
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:sqlite:C:/sqlite/db/test.db");
        dsc.setDriverName("org.sqlite.JDBC");
        dsc.setDbType(DbType.SQLITE);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);
        pc.setEntity("entity");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); // 不生成MapperXML
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        /*strategy.setSuperEntityClass("com.lion.park.entity.BaseEntity");
        strategy.setSuperEntityColumns("page","limit","sdate","edate");*/
        strategy.setRestControllerStyle(false);//restcontroller
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityLombokModel(true);//lombok注解
        strategy.setEntityBuilderModel(false);//连续set
        strategy.setInclude(table.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("tbl_"); // 表前缀，如t_user，没有就注释掉此行
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                //自定义配置，在模版中cfg.superColums 获取
                // TODO 这里解决子类会生成父类属性的问题，在模版里会用到该配置
                map.put("superColums", this.getConfig().getStrategyConfig().getSuperEntityColumns());
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
