package devgraft.module.member.app;

import devgraft.support.crypto.MD5;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileImageProvider {
    private static final int COUNT = 12;

    public static String create() {
        String encrypt = MD5.encrypt(RandomStringUtils.random(COUNT));
        return "https://secure.gravatar.com/avatar/"+ encrypt + "?s=800&d=identicon";
    }
}
