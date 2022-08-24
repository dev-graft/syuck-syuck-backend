package devgraft.member.app;

import devgraft.member.exception.MemberPasswordDecryptFailedException;
import devgraft.support.crypt.PBKDF2;
import devgraft.support.crypt.RSA;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.Base64;

@Component
public class MemberPasswordHelper {

    public String encryptPassword(String plainPassword, KeyPair keyPair) {
        try {
            return RSA.encrypt(plainPassword, keyPair.getPublic());
        }catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decryptPassword(String encryptedPassword, KeyPair keyPair) {
        try {
            return RSA.decrypt(encryptedPassword, keyPair.getPrivate());
        } catch (final Exception e) {
            throw new MemberPasswordDecryptFailedException();
        }
    }
    // 패스워드 해싱
    public String hashingPassword(String password) {
        return Base64.getEncoder().encodeToString(PBKDF2.encrypt(password));
    }
}
