package com.example.wishcycle.config;

import com.example.wishcycle.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/wishlists/**", "/**/login/profile/**", "/**/wishlist/**", "/**/item/**") // Tell Spring which URLs to watch
                .excludePathPatterns("/**/login-page", "/**/signup-page", "/**/css/**", "/**/images/**", "**/signup/save");   // And these to be excluded
    }
}
