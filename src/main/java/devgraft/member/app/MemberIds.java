package devgraft.member.app;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class MemberIds {
    private final Long id;
    private final String loginId;

    public MemberIds(Long id, String loginId) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(loginId, "loginId must not be null");
        this.id = id;
        this.loginId = loginId;
    }
}
