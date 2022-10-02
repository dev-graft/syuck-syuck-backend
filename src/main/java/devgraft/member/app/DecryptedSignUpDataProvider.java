package devgraft.member.app;

import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class DecryptedSignUpDataProvider {

    public DecryptedSignUpData create(final EncryptedSignUpRequest request, final KeyPair keyPair) {
        return null;
    }
}
