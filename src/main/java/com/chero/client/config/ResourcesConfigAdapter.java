package com.chero.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourcesConfigAdapter
        extends WebMvcConfigurerAdapter {

    @Value("${custom.server.userface.dir}")
    private String userfaceDir;
    @Value("${custom.server.upload.dir}")
    private String uploadDir;
    @Value("${custom.server.registration.dir}")
    private String registrationDir;
    @Value("${custom.server.certificate.dir}")
    private String certificateDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/userface/**").addResourceLocations("file:"+userfaceDir);
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+uploadDir);
        registry.addResourceHandler("/registration/**").addResourceLocations("file:"+registrationDir);
        registry.addResourceHandler("/certificate/**").addResourceLocations("file:"+certificateDir);
        super.addResourceHandlers(registry);
    }

}