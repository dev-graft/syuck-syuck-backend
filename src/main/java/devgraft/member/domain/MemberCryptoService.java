package devgraft.member.domain;

import java.security.KeyPair;

public interface MemberCryptoService {
    KeyPair generatedCryptoKey();

    String encrypt(final KeyPair keyPair, final String plainText);

    String decrypt(final KeyPair keyPair, final String encryptText);

    Password hashingPassword(final String password);
}
