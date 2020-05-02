package com.bz.blueauth.view;

import com.bz.blueauth.tools.AppProperties;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        AppProperties prop = AppProperties.getInstance();
        String origins = prop.get("server.allowedOrigins");
        registry
               .addMapping("/Auth/**")
               .allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE")
               .allowedOrigins(origins)
               .allowedHeaders("*");

        //registry.addMapping("/Auth/**")
          //      .allowedOrigins(origins.split(","));
    }
}