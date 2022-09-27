package devgraft.module.member.app;

import devgraft.support.crypto.MD5;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class ProfileImageProvider {
    private static final int COUNT = 12;

    public String create() {
        String encrypt = MD5.encrypt(RandomStringUtils.random(COUNT));
        return "https://secure.gravatar.com/avatar/"+ encrypt + "?s=800&d=identicon";
    }
}
