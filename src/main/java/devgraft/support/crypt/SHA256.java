package devgraft.support.crypt;

import javax.crypto.Mac;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public static String encrypt(final String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            final StringBuilder builder = new StringBuilder();
            for (byte b : md.digest()) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encrypt(final String text, final String hKey) {
        final byte[] key = hKey.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        final Key hmacSHA256SecretKey = new javax.crypto.spec.SecretKeySpec(key, "HmacSHA256");

        try {
            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(hmacSHA256SecretKey);
            return java.util.Base64.getEncoder().encodeToString(mac.doFinal(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
