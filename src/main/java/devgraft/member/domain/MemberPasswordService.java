package devgraft.member.domain;

import java.security.KeyPair;

public interface MemberPasswordService {
    public static final String PUB_KEY = "pub_key";
    public static final String PRI_KEY = "pri_key";
    public static final String KEY_PAIR = "key_pair";
    public KeyPair generateCryptoKey();
    public String encryptPassword(final String plainPassword, final KeyPair keyPair);
    public String decryptPassword(final String encryptedPassword, final KeyPair keyPair);
    public String hashingPassword(final String password);
}
