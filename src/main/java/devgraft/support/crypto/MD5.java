package devgraft.support.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MD5 {
    private static final java.security.MessageDigest md;
    static {
        try {
            md = java.security.MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new CryptoProcessException(e);
        }
    }
    public static String encrypt(final String text) {
        final java.math.BigInteger i = new java.math.BigInteger(1, md.digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        return String.format("%032x", i);
    }
}
