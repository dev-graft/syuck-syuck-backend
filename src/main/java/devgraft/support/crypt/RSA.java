package devgraft.support.crypt;

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

public class RSA {
    public static final String PUB_KEY = "pub_key";
    public static final String PRI_KEY = "pri_key";
    public static final String KEY_PAIR = "key_pair";

    public static KeyPair generatedKeyPair() throws NoSuchAlgorithmException {
        final SecureRandom secureRandom = new SecureRandom();
        final KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024, secureRandom);
        return gen.genKeyPair();
    }

    public static String encrypt(final String plainText, final PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        final byte[] bytePlain = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(bytePlain);
    }

    public static String decrypt(final String encryptedText, final PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        final Cipher cipher = Cipher.getInstance("RSA");
        final byte[] byteEncrypted = Base64.getDecoder().decode(encryptedText.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        final byte[] bytePlain = cipher.doFinal(byteEncrypted);
        return new String(bytePlain, StandardCharsets.UTF_8);
    }

    public static PrivateKey getPrivateKeyFromBase64String(final String keyString)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        final String privateKeyString =
                keyString.replaceAll("\\n", "").replaceAll("-{5}[ a-zA-Z]*-{5}", "");

        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        final PKCS8EncodedKeySpec keySpecPKCS8 =
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));

        return keyFactory.generatePrivate(keySpecPKCS8);
    }

    public static PublicKey getPublicKeyFromBase64String(final String keyString)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        final String publicKeyString =
                keyString.replaceAll("\\n", "").replaceAll("-{5}[ a-zA-Z]*-{5}", "");

        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        final X509EncodedKeySpec keySpecX509 =
                new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));

        return keyFactory.generatePublic(keySpecX509);
    }
}
