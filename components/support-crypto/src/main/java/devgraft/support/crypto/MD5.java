package devgraft.support.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MD5 {
    public static String encrypt(final String text) {
        try {
            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            final java.math.BigInteger i = new java.math.BigInteger(1, md.digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            return String.format("%032x", i);
        }catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new EncryptException(e.getMessage());
        }
    }
}
