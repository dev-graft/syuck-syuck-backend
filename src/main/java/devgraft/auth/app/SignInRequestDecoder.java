package devgraft.auth.app;

import devgraft.auth.domain.AuthCryptoService;
import devgraft.auth.domain.AuthMemberService;
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
    private final AuthCryptoService authCryptoService;

    public DecryptedSignInData decrypt(SignInService.EncryptedSignInRequest request, KeyPair keyPair) {
        return DecryptedSignInData.builder()
                .loginId(request.getLoginId())
                .password(authCryptoService.decrypt(keyPair, request.getPassword()))
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

        public AuthMemberService.AuthenticateMemberRequest toRequest() {
            return new AuthMemberService.AuthenticateMemberRequest(loginId, password);
        }
    }
}
