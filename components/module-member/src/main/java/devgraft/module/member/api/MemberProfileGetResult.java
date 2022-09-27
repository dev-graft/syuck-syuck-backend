package devgraft.module.member.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberProfileGetResult {
    private String loginId;
    private String nickname;
    private String profileImage;
    private String stateMessage;
}
