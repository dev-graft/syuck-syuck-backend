package devgraft.local;

import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import devgraft.member.domain.MemberPasswordService;
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
    private final MemberPasswordService memberPasswordService;

    @PostConstruct
    public void init() {
        final KeyPair keyPair = RSA.generatedKeyPair();
        final String encryptedPassword = memberPasswordService.encryptPassword("qweR123$", keyPair);
        membershipService.membership(MembershipRequest.builder()
                .loginId("qwerty123")
                .password(encryptedPassword)
                .nickname("nic")
                .profileImage("https://secure.gravatar.com/avatar/835628379d78a39af54f1c5ebfc050b4?s=800&d=identicon")
                .build(), keyPair);
    }
}
