package devgraft.module.member.api;

import devgraft.module.member.app.EncryptMembershipRequest;
import devgraft.module.member.app.MembershipService;
import devgraft.module.member.app.NotFoundMemberException;
import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.query.MemberData;
import devgraft.module.member.query.MemberDataDao;
import devgraft.module.member.query.MemberDataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Optional;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    public static final String KEY_PAIR = "key_pair";
    private final MembershipService membershipService;
    private final MemberCryptService memberCryptService;
    private final MemberDataDao memberDataDao;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("membership")
    public String membership(@RequestBody final EncryptMembershipRequest request, final HttpSession httpSession) {
        final KeyPair keyPair = (KeyPair) Optional.ofNullable(httpSession.getAttribute(KEY_PAIR))
                .orElseThrow(NotIssuedPublicKeyException::new);
        membershipService.membership(request, keyPair);
        return "환영합니다!";
    }

    @GetMapping("code")
    public String getPubKey(HttpSession httpSession) {
        final KeyPair keyPair = memberCryptService.generatedCryptKey();
        httpSession.setAttribute(KEY_PAIR, keyPair);
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    @GetMapping("check/{loginId}")
    public Boolean existsLoginId(@PathVariable(name = "loginId") String loginId) {
        return memberDataDao.findOne(MemberDataSpec.loggedIdEquals(loginId)
                .and(MemberDataSpec.normalEquals())).isPresent();
    }

    @GetMapping("profile/{loginId}")
    public MemberProfileGetResult getMemberProfile(@PathVariable(name = "loginId") String loginId) {
        final MemberData memberData = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(loginId)
                        .and(MemberDataSpec.normalEquals()))
                .orElseThrow(NotFoundMemberException::new);

        return MemberProfileGetResult.builder()
                .loginId(memberData.getMemberId())
                .nickname(memberData.getNickname())
                .profileImage(memberData.getProfileImage())
                .stateMessage(memberData.getStateMessage())
                .build();
    }
}
