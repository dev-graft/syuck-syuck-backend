package devgraft.auth.infra;

import devgraft.auth.domain.SignInCryptoService;
import devgraft.common.exception.UnauthenticatedPublicKeyException;
import devgraft.support.crypto.CryptoProcessException;
import devgraft.support.crypto.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class SignInCryptoServiceImpl implements SignInCryptoService {
    @Override
    public KeyPair generatedCode() {
        return RSA.generatedKeyPair("auth");
    }

    @Override
    public String decrypt(final String encryptText, final KeyPair keyPair) {
        try {
            return RSA.decrypt(encryptText, keyPair.getPrivate());
        } catch (final CryptoProcessException e) {
            e.printStackTrace();
            throw new UnauthenticatedPublicKeyException();
        }
    }

    @Override
    public String encrypt(final String plainText, final KeyPair keyPair) {
        return RSA.encrypt(plainText, keyPair.getPublic());
    }
}
