package devgraft.auth.infra;

import devgraft.auth.domain.AuthCryptoService;
import devgraft.member.domain.UnauthenticatedPublicKeyException;
import devgraft.support.crypto.CryptoProcessException;
import devgraft.support.crypto.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public class AuthCryptoServiceImpl implements AuthCryptoService {
    @Override
    public KeyPair generatedCryptoKey() {
        return RSA.generatedKeyPair("auth");
    }

    @Override
    public String decrypt(final KeyPair keyPair, final String encryptText) {
        try {
            return RSA.decrypt(encryptText, keyPair.getPrivate());
        } catch (final CryptoProcessException e) {
            e.printStackTrace();
            throw new UnauthenticatedPublicKeyException();
        }
    }
}
