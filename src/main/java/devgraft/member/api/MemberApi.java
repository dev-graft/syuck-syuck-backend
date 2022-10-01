package devgraft.member.api;

import devgraft.member.app.LoginRequest;
import devgraft.member.app.LoginService;
import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import devgraft.member.domain.MemberPasswordService;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import devgraft.support.advice.CommonResult;
import devgraft.support.advice.SingleResult;
import devgraft.support.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Optional;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberDataDao memberDataDao;
    private final MembershipService membershipService;
    private final LoginService loginService;

    @PostMapping
    public CommonResult membership(@RequestBody final MembershipRequest request, final HttpSession httpSession) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(MemberPasswordService.KEY_PAIR)).orElseThrow(NotFindCodeException::new);
        membershipService.membership(request, keyPair);
        httpSession.removeAttribute(MemberPasswordService.KEY_PAIR);
        return CommonResult.success(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody final LoginRequest request, final HttpSession httpSession) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(MemberPasswordService.KEY_PAIR)).orElseThrow(NotFindCodeException::new);
        loginService.login(request, keyPair);
        httpSession.removeAttribute(MemberPasswordService.KEY_PAIR);
        return CommonResult.success(HttpStatus.OK);
    }

    @GetMapping("check")
    public SingleResult<Boolean> existsLoginId(@RequestParam(name = "loginId") String loginId) {
        boolean exists = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(loginId)
                .and(MemberDataSpec.normalEquals())).isPresent();
        return SingleResult.success(exists);
    }

    @GetMapping("{loginId}")
    public MemberProfileGetResult getMemberProfile(@PathVariable(name = "loginId") final String loginId) {
        final Optional<MemberData> memberDataOpt = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(loginId)
                .and(MemberDataSpec.normalEquals()));

        final MemberData memberData = memberDataOpt.orElseThrow(() -> new NoContentException("존재하지 않는 회원입니다."));

        return MemberProfileGetResult.builder()
                .loginId(memberData.getLoggedId())
                .nickname(memberData.getNickname())
                .profileImage(memberData.getProfileImage())
                .stateMessage(memberData.getStateMessage())
                .build();
    }
}
