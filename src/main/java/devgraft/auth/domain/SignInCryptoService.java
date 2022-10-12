package devgraft.auth.domain;

import java.security.KeyPair;

public interface SignInCryptoService {

    KeyPair generatedCode();

    String decrypt(final String encryptText, final KeyPair keyPair);
    String encrypt(final String plainText, final KeyPair keyPair);
}
