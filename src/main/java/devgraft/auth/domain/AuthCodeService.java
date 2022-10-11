package devgraft.auth.domain;

import devgraft.common.wrap.ProcessOptional;
import devgraft.common.wrap.ProcessResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AuthCodeService {
    AuthCodeGeneratedResult generate(final String uniqId); // << SignInService, SignInRefreshService

    // 전달받은 키가 만료되었는지 검증
    AuthCodeExpiredResult expired(final String code);
    // refresh토큰의 검증과 accessToken과 키쌍관계인지 검증
    AuthCodeVerifyResult verify(final AuthorizationElements authorizationElements);


    @AllArgsConstructor
    @Getter
    class AuthCodeGeneratedResult implements AuthorizationElements {
        private final String accessToken;
        private final String refreshToken;
    }

    class AuthCodeExpiredResult extends ProcessResult {
        public AuthCodeExpiredResult(final String message, final boolean success) {
            super(message, success);
        }
    }

    class AuthCodeVerifyResult extends ProcessOptional<String> {
        public AuthCodeVerifyResult(final String value, final String message, final boolean success) {
            super(value, message, success);
        }
    }
}
