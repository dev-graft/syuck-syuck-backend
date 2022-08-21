package devgraft.member.api;

import devgraft.member.app.MemberId;
import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MembershipService membershipService;

    @GetMapping
    public MemberId membership(){//@RequestBody final MembershipRequest request) {
        return membershipService.membership(MembershipRequest.builder()
                        .memberId("qweqwe123")
                        .password("Zqwe123!")
                        .nickname("AA")
                .build());
    }
}
