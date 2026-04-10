package com.fsad.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Global CORS config – allows React frontend to call the backend.
 * In production with Nginx reverse proxy on the same domain,
 * CORS is not needed (same origin). Keep this for dev flexibility.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    /**
     * TASK 5 (Alternative): Serve React build from Spring Boot static folder.
     * Copy React's build/ output to src/main/resources/static/
     * Then Spring Boot serves the frontend at http://localhost:8080/
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
