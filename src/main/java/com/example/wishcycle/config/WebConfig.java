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
                .addPathPatterns( // Tell Spring which URLs to watch
                        "/**/homepage",
                        "/**/wishlists/**",
                        "/**/wishlist/**",
                        "/**/social-wishlists/**", // Added to protect friend lists
                        "/**/login/profile/**",
                        "/**/item/**"
                ) // Tell Spring which URLs to watch

                .excludePathPatterns( // And these to be excluded
                        "/**/index",           // The landing page
                        "/**/login-page",      // Need to see this to log in!
                        "/**/signup-page",     // Need to see this to join!
                        "/**/login/save",      // The POST route for logging in
                        "/**/signup/save",     // The POST route for signing up
                        "/**/about-us",        // Usually public
                        "/**/css/**",          // Static files must be public
                        "/**/images/**",
                        "/**/js/**",
                        "/**/register"
                );
    }
}
