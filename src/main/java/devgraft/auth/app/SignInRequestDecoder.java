package devgraft.auth.app;

import devgraft.auth.domain.SignInCryptoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@RequiredArgsConstructor
@Component
public class SignInRequestDecoder {
    private final SignInCryptoService signInCryptoService;

    public DecryptedSignInData decrypt(final SignInService.EncryptedSignInRequest request, final KeyPair keyPair) {
        return DecryptedSignInData.builder()
                .loginId(request.getLoginId())
                .password(signInCryptoService.decrypt(request.getPassword(), keyPair))
                .pushToken(request.getPushToken())
                .os(request.getOs())
                .deviceName(request.getDeviceName())
                .build();
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DecryptedSignInData {
        private String loginId;
        private String password;
        private String os;
        private String pushToken;
        private String deviceName;
    }
}
