package devgraft.local;

import devgraft.member.app.MemberPasswordHelper;
import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import devgraft.support.crypt.RSA;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;

@Profile("local")
@RequiredArgsConstructor
@Component
public class InitLogic {
    private final MembershipService membershipService;
    private final MemberPasswordHelper memberPasswordHelper;

    @PostConstruct
    public void init() {
        final KeyPair keyPair = RSA.generatedKeyPair();
        final String encryptedPassword = memberPasswordHelper.encryptPassword("qweR123$", keyPair);
        membershipService.membership(MembershipRequest.builder()
                .loginId("qwerty123")
                .password(encryptedPassword)
                .nickname("nic")
                .build(), keyPair);
    }
}
