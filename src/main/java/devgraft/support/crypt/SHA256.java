package devgraft.support.crypt;

public class SHA256 {
    public static String encrypt(String text) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder builder = new StringBuilder();
            for (byte b : md.digest()) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encrypt(String text, String hKey) {
        byte[] key = hKey.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        javax.crypto.spec.SecretKeySpec hmacSHA256SecretKey = new javax.crypto.spec.SecretKeySpec(key, "HmacSHA256");

        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            mac.init(hmacSHA256SecretKey);
            return java.util.Base64.getEncoder().encodeToString(mac.doFinal(text.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
