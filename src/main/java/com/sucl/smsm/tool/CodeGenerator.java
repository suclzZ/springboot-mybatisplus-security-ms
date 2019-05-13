package com.sucl.smsm.tool;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @since 2018/12/8
 */
public class CodeGenerator {

    /**
     * 配置模块与表
     */
    public static Map<String,List<String>> moduleTableMap = Maps.newHashMap();

    static{
        moduleTableMap.put("system",Arrays.asList("user","dept","role","perm","menu"));
    }

    public static void main(String[] args) {
        autoGenerator();
    }

    public static void autoGenerator(){
        if(MapUtils.isNotEmpty(moduleTableMap)){
            for(Map.Entry<String,List<String>> entry : moduleTableMap.entrySet()){
                List<String> tables = entry.getValue();
                if(CollectionUtils.isNotEmpty(tables)){
                    tables.stream().forEach(e->{
                        // 代码生成器
                        AutoGenerator mpg = new AutoGenerator();
                        mpg.setTemplate( new TemplateConfig());
                        //全局配置
                        String projectPath = globalConfig(mpg);
                        //数据库配置
                        dataSourceConfig(mpg);
                        //包配置
                        PackageConfig pc =packageConfig(mpg,entry.getKey());
                        injectionConfig(mpg,pc,projectPath);
                        strategyConfig(mpg,pc,e);
                        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
                        mpg.execute();
                    });
                }
            }
        }else{
            throw new RuntimeException("请配置模块");
        }
    }

    public static String globalConfig(AutoGenerator mpg){
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("sucl");
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setIdType(IdType.UUID);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);
        return projectPath;
    }

    public static void dataSourceConfig(AutoGenerator mpg){
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/smsm?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("smsm");
        dsc.setPassword("smsm");
        mpg.setDataSource(dsc);
    }

    public static PackageConfig packageConfig(AutoGenerator mpg,String moduleName){
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);//模块名
        pc.setParent("com.sucl.smsm");
        mpg.setPackageInfo(pc);
        return pc;
    }

    public static void injectionConfig(AutoGenerator mpg,PackageConfig pc,String projectPath){
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String outPath = projectPath+"/src/main/java/"+pc.getParent().replaceAll("\\.","/")
                        +"/mapper/"+tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                // 自定义输入文件名称
//                outPath = projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return outPath;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));
    }

    public static void strategyConfig(AutoGenerator mpg, PackageConfig pc, String tableName){
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.sucl.smsm.core.model.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass("com.sucl.smsm.core.web.BaseController");
        strategy.setInclude(tableName);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
    }
}
