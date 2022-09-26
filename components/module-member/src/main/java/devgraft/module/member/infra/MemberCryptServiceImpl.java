package devgraft.module.member.infra;

import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MemberDecryptException;
import devgraft.module.member.domain.MemberEncryptException;
import devgraft.module.member.domain.Password;
import devgraft.support.crypto.DecryptException;
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
        }catch (final Exception e) {
            throw new MemberEncryptException(e.getMessage());
        }
    }

    @Override
    public String decrypt(final KeyPair keyPair, final String encryptText) {
        try {
            return RSA.decrypt(encryptText, keyPair.getPrivate());
        } catch (final DecryptException e) {
            throw new MemberDecryptException();
        }
    }

    @Override
    public Password hashingPassword(final String password) {
        final String encodePassword = Base64.getEncoder().encodeToString(PBKDF2.encrypt(password));
        return Password.from(encodePassword);
    }
}
