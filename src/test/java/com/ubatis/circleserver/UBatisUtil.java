package com.ubatis.circleserver;

import com.ubatis.circleserver.config.GeneratorConfig;
import com.ubatis.circleserver.util.generator.BeanGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BeanGenerator.class,GeneratorConfig.class})
public class UBatisUtil {

    @Autowired
    private BeanGenerator beanGenerator;

    @Test
    public void contextLoads() {

    }

    /**
     * 刷新本地Bean代码
     */
    @Test
    public void refreshBeans(){
        beanGenerator.initAndStart();
    }

}
