package devgraft.module.member.app;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DecryptMembershipRequest {
    private String loginId;
    private String password;
    private String nickname;
    private String profileImage;
}

