package devgraft.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AuthorizationCodeService {
    SignInAuthorizationResult generate(final String uniqId); // << SignInService, SignInRefreshService


    @AllArgsConstructor
    @Getter
    class SignInAuthorizationResult implements AuthorizationElements {
        private final String accessToken;
        private final String refreshToken;
    }
}
