package org.deslre.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${custom.static-source-path}")
    private String staticSourcePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("staticSourcePath = " + staticSourcePath);
        // 注意：file: 后面必须以 / 结尾，且路径分隔符为 /
        String location = "file:" + staticSourcePath;
        if (!location.endsWith("/")) {
            location += "/";
        }
        registry.addResourceHandler("/staticSource/**")
                .addResourceLocations(location);
    }
}
