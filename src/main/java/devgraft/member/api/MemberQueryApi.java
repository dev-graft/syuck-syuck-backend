package devgraft.member.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class MemberQueryApi {
    private final MemberDataDao memberDataDao;

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/exists")
    public boolean isExistsLoginId(@RequestParam(name = "loginId") String loginId) {
        return memberDataDao.findOne(MemberDataSpec.loggedIdEquals(loginId)
                .and(MemberDataSpec.normalEquals())).isPresent();
    }

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX)
    public MemberProfileGetResult getMemberProfile(@RequestParam(name = "loginId") String loginId) {
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

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/self")
    public MemberProfileGetResult getMemberProfile(@Credentials MemberCredentials memberCredentials) {
        final MemberData memberData = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(memberCredentials.getMemberId())
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
