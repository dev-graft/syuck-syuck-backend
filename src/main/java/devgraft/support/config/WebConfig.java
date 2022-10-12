package devgraft.support.config;

import devgraft.common.credential.MemberCredentialsResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final MemberCredentialsResolver memberCredentialsResolver;
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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberCredentialsResolver);
    }
}
