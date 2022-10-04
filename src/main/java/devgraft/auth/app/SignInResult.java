package devgraft.auth.app;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignInResult {
    private final String accessToken;
    private final String refreshToken;

    public static SignInResult of(final String accessToken, final String refreshToken) {
        return new SignInResult(accessToken, refreshToken);
    }
}
