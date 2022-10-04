package devgraft.member.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class MemberMatchRequest {
    private final String loginId;
    private final String password;

    private MemberMatchRequest(final String loginId, final String password) {
        Assert.notNull(loginId, "MemberMatchRequest.loginId must not be null");
        Assert.notNull(password, "MemberMatchRequest.password must not be null");
        this.loginId = loginId;
        this.password = password;
    }

    public static MemberMatchRequest of(final String loginId, final String password) {
        return new MemberMatchRequest(loginId, password);
    }
}
