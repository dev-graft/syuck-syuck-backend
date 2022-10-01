package devgraft.module.member.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MembershipDecryptedData {
    private final String loginId;
    private final String password;
    private final String nickname;
    private final String profileImage;
}

