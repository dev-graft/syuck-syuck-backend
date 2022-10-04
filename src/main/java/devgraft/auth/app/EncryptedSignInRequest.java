package devgraft.auth.app;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class EncryptedSignInRequest {
    private String loginId;
    private String password;
    private String pushToken;
    private String os;
    private String deviceName;
}
