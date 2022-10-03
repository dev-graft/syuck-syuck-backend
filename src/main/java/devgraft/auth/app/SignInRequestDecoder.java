package devgraft.auth.app;

import devgraft.auth.domain.DecryptedSignInData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@RequiredArgsConstructor
@Component
public class SignInRequestDecoder {

    public DecryptedSignInData decrypt(EncryptedSignInRequest request, KeyPair keyPair) {
        return null;
    }
}
