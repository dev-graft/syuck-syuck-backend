package devgraft.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

public interface MemberAuthenticationService {
    AuthenticateMemberResult authenticate(AuthenticateMemberRequest request);

    /**
     * 회원 정보 인증 요청문
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class AuthenticateMemberRequest {
        private String loginId;
        private String password;

        public AuthenticateMemberRequest(final String loginId, final String password) {
            Assert.notNull(loginId, "AuthenticateMemberRequest.loginId must not be null");
            Assert.notNull(password, "AuthenticateMemberRequest.password must not be null");
            this.loginId = loginId;
            this.password = password;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class AuthenticateMemberResult {
        private String message;
        private boolean success;
    }
}
