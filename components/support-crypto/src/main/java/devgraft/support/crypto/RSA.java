package devgraft.support.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RSA {
    private static final KeyPairGenerator keyPairGenerator;
    private static final Cipher cipher;
    private static final KeyFactory keyFactory;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            cipher = Cipher.getInstance("RSA");
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new CryptoProcessException(e);
        }
    }

    public static KeyPair generatedKeyPair() {
        final SecureRandom secureRandom = new SecureRandom();
        keyPairGenerator.initialize(1024, secureRandom);
        return keyPairGenerator.genKeyPair();
    }

    public static String encrypt(final String plainText, final PublicKey publicKey) throws CryptoProcessException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            final byte[] bytePlain = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytePlain);
        } catch (final IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new CryptoProcessException(e);
        }
    }

    public static String decrypt(final String encryptedText, final PrivateKey privateKey) throws CryptoProcessException {
        try {
            final byte[] byteEncrypted = Base64.getDecoder().decode(encryptedText.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            final byte[] bytePlain = cipher.doFinal(byteEncrypted);
            return new String(bytePlain, StandardCharsets.UTF_8);
        } catch (final IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new CryptoProcessException(e);
        }
    }

    public static PrivateKey getPrivateKeyFromBase64String(final String keyString) throws CryptoProcessException {
        try {
            final String privateKeyString =
                    keyString.replaceAll("\\n", "")
                            .replaceAll("-{5}[ a-zA-Z]*-{5}", "");

            final PKCS8EncodedKeySpec keySpecPKCS8 =
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));

            return keyFactory.generatePrivate(keySpecPKCS8);
        } catch (final InvalidKeySpecException e) {
            throw new CryptoProcessException(e);
        }
    }

    public static PublicKey getPublicKeyFromBase64String(final String keyString) throws CryptoProcessException {
        try {
            final String publicKeyString =
                    keyString.replaceAll("\\n", "")
                            .replaceAll("-{5}[ a-zA-Z]*-{5}", "");

            final X509EncodedKeySpec keySpecX509 =
                    new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));

            return keyFactory.generatePublic(keySpecX509);
        } catch (final InvalidKeySpecException e) {
            throw new CryptoProcessException(e);
        }
    }
}
