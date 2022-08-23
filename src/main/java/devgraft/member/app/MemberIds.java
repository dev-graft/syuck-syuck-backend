package devgraft.member.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberIds {
    private final Long id;
    private final String loginId;

    public static MemberIds of(final Long id, final String loginId) {
        return new MemberIds(id, loginId);
    }
}
