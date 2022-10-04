package devgraft.auth.app;

import devgraft.auth.domain.AuthCryptoService;
import devgraft.auth.domain.DecryptedSignInData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@RequiredArgsConstructor
@Component
public class SignInRequestDecoder {
    private final AuthCryptoService authCryptoService;

    public DecryptedSignInData decrypt(EncryptedSignInRequest request, KeyPair keyPair) {
        final String decryptedPassword = authCryptoService.decrypt(keyPair, request.getPassword());

        return DecryptedSignInData.builder()
                .loginId(request.getLoginId())
                .password(decryptedPassword)
                .pushToken(request.getPushToken())
                .os(request.getOs())
                .deviceName(request.getDeviceName())
                .build();
    }
}
