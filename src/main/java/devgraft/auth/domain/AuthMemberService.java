package devgraft.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface AuthMemberService {
    AuthenticateMemberResult authenticate(AuthenticateMemberRequest request);

    /**
     * 회원 정보 인증 요청문
     */
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class AuthenticateMemberRequest {
        private String loginId;
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class AuthenticateMemberResult {
        private String message;
        private boolean success;
    }
}
