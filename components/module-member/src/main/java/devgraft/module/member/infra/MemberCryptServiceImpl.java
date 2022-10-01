package devgraft.module.member.infra;

import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MemberEncryptException;
import devgraft.module.member.domain.Password;
import devgraft.module.member.domain.UnauthenticatedPublicKeyException;
import devgraft.support.crypto.CryptoProcessException;
import devgraft.support.crypto.PBKDF2;
import devgraft.support.crypto.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Base64;

@Service
public class MemberCryptServiceImpl implements MemberCryptService {
    @Override
    public KeyPair generatedCryptKey() {
        return RSA.generatedKeyPair();
    }

    @Override
    public String encrypt(final KeyPair keyPair, final String plainText) {
        try {
            return RSA.encrypt(plainText, keyPair.getPublic());
        } catch (final CryptoProcessException e) {
            e.printStackTrace();
            throw new MemberEncryptException();
        }
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

    @Override
    public Password hashingPassword(final String password) throws CryptoProcessException {
        final byte[] encrypt = PBKDF2.encrypt(password);
        final String encodePassword = Base64.getEncoder().encodeToString(encrypt);
        return Password.from(encodePassword);
    }
}
