package devgraft.member.app;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EncryptedSignUpRequest {
    private String loginId;
    private String password;
    private String nickname;
    private String profileImage;
}
