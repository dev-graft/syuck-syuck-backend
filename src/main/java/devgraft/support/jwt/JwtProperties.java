package devgraft.support.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@ConstructorBinding
@ConfigurationProperties(prefix = "support.jwt")
public class JwtProperties {
    private final String secret;
    private final Key signKey;

    public JwtProperties(String secret) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
        this.signKey = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getSecret() {
        return secret;
    }

    public Key getSignKey() {
        return signKey;
    }

    @Override
    public String toString() {
        return "JwtProperties{" +
                "secretKey='" + secret + '\'' +
                '}';
    }
}
