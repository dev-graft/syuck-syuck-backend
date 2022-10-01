package devgraft.support.advice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9001")
                .allowedOrigins("*")
//                .allowedOrigins(domainProperties.getBaseUrl(), "http://localhost:8084", "https://10.10.40.139")
                .allowCredentials(false)
//                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders(
                        "token",
                        "fcm-token",
                        "refresh",
                        "Vary",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                        )
                .maxAge(1728000);
    }
}
