package com.ubatis.circleserver;

import com.ubatis.circleserver.config.GeneratorConfig;
import com.ubatis.circleserver.util.generator.GenMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanGenerator {

    private final static Logger logger = LoggerFactory.getLogger(BeanGenerator.class);

    @Autowired
    private GeneratorConfig generatorConfig;

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String jdbc_driver;

    @Value("${spring.datasource.url}")
    private String jdbc_url;

    @Value("${spring.datasource.hikari.username}")
    private String jdbc_username;

    @Value("${spring.datasource.hikari.password}")
    private String jdbc_password;

    @Value("${database.name}")
    private String database_name;

    @Test
    public void contextLoads() {

    }

    /**
     * 刷新本地Bean代码
     */
    @Test
    public void refreshBeans(){
        GenMain.init(jdbc_driver, jdbc_url, jdbc_username, jdbc_password, database_name);
        GenMain.refreshBeans(generatorConfig);
    }

}
