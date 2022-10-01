package devgraft.support.crypto;

import java.security.KeyPair;

public class KeyPairFixture {
    public static KeyPair anKeyPair() {
        return RSA.generatedKeyPair();
    }
}
