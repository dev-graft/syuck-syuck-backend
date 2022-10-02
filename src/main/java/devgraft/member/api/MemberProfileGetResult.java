package devgraft.member.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MemberProfileGetResult {
    private final String loginId;
    private final String nickname;
    private final String profileImage;
    private final String stateMessage;
}
