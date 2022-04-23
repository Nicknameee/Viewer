package application.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationMVCConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/uploads/");
        registry
                .addResourceHandler("/user/**")
                .addResourceLocations("classpath:/static/user/");
        registry
                .addResourceHandler("/manager/**")
                .addResourceLocations("classpath:/static/manager/");
    }
}