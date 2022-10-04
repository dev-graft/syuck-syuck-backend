package devgraft.support.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtIssuedResult {
    private final String accessToken;
    private final String refreshToken;

    public static JwtIssuedResult of(final String accessToken, final String refreshToken) {
        return new JwtIssuedResult(accessToken, refreshToken);
    }
}
