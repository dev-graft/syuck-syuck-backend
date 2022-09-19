package devgraft.member.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PUBLIC)
@Getter
public class MemberProfileGetResult {
    private final String loginId;
    private final String nickname;
    private final String profileImage;
    private final String stateMessage;
}
