package devgraft.local;

import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("local")
@RequiredArgsConstructor
@Component
public class InitLogic {
    private final MembershipService membershipService;
    @PostConstruct
    public void init() {
        membershipService.membership(MembershipRequest.builder()
                        .loginId("qwerty123")
                        .password("qweR123$")
                        .nickname("nic")
                .build());
    }
}
