package com.book.config;

import com.book.interceptor.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author sunlongfei
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private Identity identity;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(identity)
            .addPathPatterns("/**")
            .excludePathPatterns("/user/login", "/user/register");
        super.addInterceptors(registry);
    }
}
