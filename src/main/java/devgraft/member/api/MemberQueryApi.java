package devgraft.member.api;

import devgraft.common.SearchResult;
import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.MEMBER_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class MemberQueryApi {
    public static final int SIZE = 20;
    private final MemberDataDao memberDataDao;

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/exists")
    public boolean isExistsLoginId(@RequestParam(name = "target") String target) {
        return memberDataDao.findOne(MemberDataSpec.loggedIdEquals(target)
                .and(MemberDataSpec.normalEquals())).isPresent();
    }

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/profile")
    public MemberProfileGetResult getMemberProfile(@RequestParam(name = "target") String target) {
        final MemberData memberData = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(target)
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

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/nickname")
    public SearchResult<MemberInfo> nicknameSearch(@RequestParam(name = "keyword") final String keyword, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<MemberData> memberDataPages = memberDataDao.findAll(MemberDataSpec.nicknameLike(keyword).and(MemberDataSpec.normalEquals()), PageRequest.of(page, SIZE));
        final List<MemberInfo> members = memberDataPages.stream()
                .map(MemberInfo::from)
                .collect(Collectors.toList());

        return SearchResult.<MemberInfo>builder()
                .totalPage(memberDataPages.getTotalPages())
                .totalElements(memberDataPages.getTotalElements())
                .page(page)
                .size(members.size())
                .values(members)
                .build();
    }

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + MEMBER_URL_PREFIX + "/login-id")
    public SearchResult<MemberInfo> loginIdSearch(@RequestParam(name = "keyword") final String keyword, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<MemberData> memberDataPages = memberDataDao.findAll(MemberDataSpec.loggedIdLike(keyword).and(MemberDataSpec.normalEquals()), PageRequest.of(page, SIZE));
        final List<MemberInfo> members = memberDataPages.stream()
                .map(MemberInfo::from)
                .collect(Collectors.toList());

        return SearchResult.<MemberInfo>builder()
                .totalPage(memberDataPages.getTotalPages())
                .totalElements(memberDataPages.getTotalElements())
                .page(page)
                .size(members.size())
                .values(members)
                .build();
    }

    @Getter
    public static class MemberInfo {
        private final String loginId;
        private final String nickname;
        private final String profileImage;

        private MemberInfo(final String loginId, final String nickname, final String profileImage) {
            this.loginId = loginId;
            this.nickname = nickname;
            this.profileImage = profileImage;
        }

        public static MemberInfo from(final MemberData memberData) {
            return new MemberInfo(memberData.getMemberId(), memberData.getNickname(), memberData.getProfileImage());
        }
    }
}
