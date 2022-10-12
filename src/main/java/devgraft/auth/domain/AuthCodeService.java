package devgraft.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AuthCodeService {
    AuthCodeGeneratedResult generate(final String uniqId); // << SignInService, SignInRefreshService
    void expired(final String code) throws AuthCodeExpiredException, AuthCodeValidationFailedException;
    AuthCodeVerifyResult verify(final AuthorizationElements authorizationElements);


    @AllArgsConstructor
    @Getter
    class AuthCodeGeneratedResult implements AuthorizationElements {
        private final String accessToken;
        private final String refreshToken;
    }

    @AllArgsConstructor
    @Getter
    class AuthCodeVerifyResult {
        private final String uniqId;
    }
}
