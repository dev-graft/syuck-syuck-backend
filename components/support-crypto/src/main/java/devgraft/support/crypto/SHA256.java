package devgraft.support.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SHA256 {
    private static final MessageDigest md;
    private static final Mac mac;

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new CryptoProcessException(e);
        }
    }

    public static String encrypt(final String text) {
        md.update(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        final StringBuilder builder = new StringBuilder();
        for (byte b : md.digest()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String encrypt(final String text, final String hKey) throws CryptoProcessException {
        try {
            final byte[] key = hKey.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            final Key hmacSHA256SecretKey = new javax.crypto.spec.SecretKeySpec(key, "HmacSHA256");
            mac.init(hmacSHA256SecretKey);
            return java.util.Base64.getEncoder()
                    .encodeToString(mac.doFinal(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (final InvalidKeyException e) {
            throw new CryptoProcessException(e);
        }
    }
}
