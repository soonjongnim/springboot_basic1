package com.soon9086.basic1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "downloads" 폴더를 정적 리소스로 매핑
        registry.addResourceHandler("/downloads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/downloads/");
    }
}
