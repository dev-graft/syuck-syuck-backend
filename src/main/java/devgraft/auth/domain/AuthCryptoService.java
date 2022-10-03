package devgraft.auth.domain;

import java.security.KeyPair;

public interface AuthCryptoService {
    KeyPair generatedCryptoKey();
}
