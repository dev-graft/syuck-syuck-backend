package devgraft.auth.domain;

import java.security.KeyPair;

public interface AuthCryptoService {
    KeyPair generatedCryptoKey();
    String decrypt(final KeyPair keyPair, final String encryptText);
}
