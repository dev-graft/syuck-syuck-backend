package devgraft.member.app;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequest {
    private String loginId;
    private String password;

    @Builder
    public LoginRequest(final String loginId, final String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
