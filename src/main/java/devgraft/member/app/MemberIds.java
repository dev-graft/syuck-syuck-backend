package devgraft.member.app;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberIds {
    private final Long id;
    private final String loginId;

    public static MemberIds of(final Long id, final String loginId) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(loginId, "loginId must not be null");
        return new MemberIds(id, loginId);
    }
}
