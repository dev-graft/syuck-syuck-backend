package devgraft.auth.infra;

import devgraft.auth.domain.AuthCryptoService;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class AuthCryptoServiceImpl implements AuthCryptoService {
    @Override
    public KeyPair generatedCryptoKey() {
        return null;
    }

    @Override
    public String decrypt(final KeyPair keyPair, final String encryptText) {
        return null;
    }
}
