package devgraft.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DecryptedSignInData {
    private String loginId;
    private String password;

    public AuthenticateMemberRequest toRequest() {
        return new AuthenticateMemberRequest(loginId, password);
    }
}
