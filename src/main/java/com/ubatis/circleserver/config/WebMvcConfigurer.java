package com.ubatis.circleserver.config;

import com.ubatis.circleserver.handler.MyInputResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/** ref: http://www.jianshu.com/p/01a6a61d9e02
 * Created by lance on 2017/10/18.
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(myInputResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public MyInputResolver myInputResolver() {
        return new MyInputResolver();
    }

}
