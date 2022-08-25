package devgraft.member.api;

import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberQueryService;
import devgraft.support.crypt.RSA;
import devgraft.support.exception.NoContentException;
import devgraft.support.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Optional;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberQueryService memberQueryService;
    private final MembershipService membershipService;

    // TODO 회원가입 이동 예정

    @PostMapping
    public CommonResult membership(@RequestBody final MembershipRequest request, final HttpSession httpSession) { //@SessionAttribute(name = RSA.KEY_PAIR) final KeyPair keyPair) {
        KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(RSA.KEY_PAIR)).orElseThrow(RuntimeException::new);
        membershipService.membership(request, keyPair);
        httpSession.removeAttribute(RSA.KEY_PAIR);
        return CommonResult.success(HttpStatus.CREATED);
    }

    @GetMapping("{loginId}")
    public MemberProfileGetResult getMemberProfile(@PathVariable(name = "loginId") final String loginId) {
        final MemberData memberData = memberQueryService.getMemberData(loginId)
                .orElseThrow(() -> new NoContentException("존재하지 않는 회원입니다."));

        return MemberProfileGetResult.builder()
                .loginId(memberData.getLoginId())
                .nickname(memberData.getNickname())
                .profileImage(memberData.getProfileImage())
                .stateMessage(memberData.getStateMessage())
                .createdAt(memberData.getCreatedAt())
                .build();
    }

}
