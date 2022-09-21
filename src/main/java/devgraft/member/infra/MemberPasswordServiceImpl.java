package devgraft.member.infra;

import devgraft.member.app.MembershipDecryptFailedException;
import devgraft.member.domain.MemberPasswordService;
import devgraft.support.crypt.DecryptException;
import devgraft.support.crypt.EncryptException;
import devgraft.support.crypt.PBKDF2;
import devgraft.support.crypt.RSA;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Base64;

@Component
public class MemberPasswordServiceImpl implements MemberPasswordService {
    @Override
    public KeyPair generateCryptoKey() {
        return RSA.generatedKeyPair();
    }

    @Override
    public String encryptPassword(final String plainPassword, final KeyPair keyPair) {
        try {
            return RSA.encrypt(plainPassword, keyPair.getPublic());
        }catch (final Exception e) {
            throw new EncryptException(e.getMessage());
        }
    }
    @Override
    public String decryptPassword(final String encryptedPassword, final KeyPair keyPair) throws MembershipDecryptFailedException {
        try {
            return RSA.decrypt(encryptedPassword, keyPair.getPrivate());
        } catch (final DecryptException e) {
            throw new MembershipDecryptFailedException();
        }
    }
    @Override
    public String hashingPassword(final String password) {
        return Base64.getEncoder().encodeToString(PBKDF2.encrypt(password));
    }
}
