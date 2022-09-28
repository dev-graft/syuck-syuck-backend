package devgraft.support.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PBKDF2 {
    private static final String DEFAULT_PRF = "PBKDF2WithHmacSHA1"; // 난수
    private static final byte[] DEFAULT_SALT = "AEGTGRK3FN#R!@EU$IRFWS!#e4RFEKSF".getBytes(StandardCharsets.UTF_8); // 솔트
    private static final int DEFAULT_ITERATION = 65536; // 반복 횟수
    private static final int DEFAULT_D_LEN = 128; // 다이제스트 길이
    private static final SecretKeyFactory factory;

    static {
        try {
            factory = SecretKeyFactory.getInstance(DEFAULT_PRF);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new CryptoProcessException(e);
        }
    }

    public static byte[] encrypt(final String text) throws CryptoProcessException {
        try {
            final KeySpec spec = new PBEKeySpec(text.toCharArray(), DEFAULT_SALT, DEFAULT_ITERATION, DEFAULT_D_LEN);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new CryptoProcessException(e);
        }
    }
}
