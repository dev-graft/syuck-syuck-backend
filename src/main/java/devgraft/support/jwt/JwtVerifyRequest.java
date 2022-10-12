package devgraft.support.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtVerifyRequest {
    private final String accessToken;
    private final String refreshToken;
}
