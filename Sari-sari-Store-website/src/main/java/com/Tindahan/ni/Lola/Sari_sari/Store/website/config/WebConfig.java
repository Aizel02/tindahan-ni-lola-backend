package com.Tindahan.ni.Lola.Sari_sari.Store.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Use env var FRONTEND_URL if provided (useful on Render + Vercel). Otherwise allow all for dev.
    private final String frontendUrl = System.getenv("FRONTEND_URL") != null
            ? System.getenv("FRONTEND_URL")
            : "*";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendUrl.equals("") ? "*" : frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false); // set true if you need cookies/auth
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Resolve uploads directory (can be relative or absolute). If UPLOAD_DIR env var is set use it.
        String configured = System.getenv("UPLOAD_DIR");
        Path uploadDir = (configured == null || configured.isBlank())
                ? Paths.get("uploads")
                : Paths.get(configured);

        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Serve files under /uploads/** from file system absolute path
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(3600);
    }
}
