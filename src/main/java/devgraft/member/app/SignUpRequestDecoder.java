package devgraft.member.app;

import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class SignUpRequestDecoder {
    public DecryptedSignUpData decrypt(final EncryptedSignUpRequest request, final KeyPair keyPair) {
        return null;
    }
}
