package com.zzwl.ias.configuration;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * PageHelper配置类
 */
@Configuration
public class PageHelperConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public PageHelper pageHelper() {
        logger.info("------Register MyBatis PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("helperDialect", "mysql");
        //使用参数方式
        p.setProperty("supportMethodsArguments", "true");
        p.setProperty("params", "pageNum=pageNumKey;pageSize=pageSizeKey;");
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
