package devgraft.support.crypt;

public class MD5 {
    public static String encrypt(String text) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            java.math.BigInteger i = new java.math.BigInteger(1, md.digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            return String.format("%032x", i);
        }catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
