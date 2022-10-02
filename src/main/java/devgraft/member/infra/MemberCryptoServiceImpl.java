package devgraft.member.infra;

import devgraft.member.domain.MemberCryptoService;
import devgraft.member.domain.MemberEncryptException;
import devgraft.member.domain.Password;
import devgraft.member.domain.UnauthenticatedPublicKeyException;
import devgraft.support.crypto.CryptoProcessException;
import devgraft.support.crypto.PBKDF2;
import devgraft.support.crypto.RSA;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Base64;

@Service
public class MemberCryptoServiceImpl implements MemberCryptoService {

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
    public Password hashingPassword(final String password) {
        final byte[] encrypt = PBKDF2.encrypt(password);
        final String encodePassword = Base64.getEncoder().encodeToString(encrypt);
        return Password.from(encodePassword);
    }
}
