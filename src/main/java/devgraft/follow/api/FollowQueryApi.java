package devgraft.follow.api;

import devgraft.common.SearchResult;
import devgraft.follow.query.FollowData;
import devgraft.follow.query.FollowDataDao;
import devgraft.follow.query.FollowDataSpec;
import devgraft.member.domain.FindMemberIdsService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class FollowQueryApi {
    private static final int SIZE = 20;
    private final FollowDataDao followDataDao;
    private final FindMemberIdsService findMemberIdsService;

    // target이 팔로우 한 사람
    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX + "/following")
    public SearchResult<FollowMemberInfo> searchFollowing(@RequestParam(name = "target") String target, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<FollowData> followDataPage = followDataDao.findAll(FollowDataSpec.followerIdEquals(target), PageRequest.of(page, SIZE));

        return getFollowMemberInfoSearchResult(page, followDataPage, followDataPage.stream().map(FollowData::getFollowingId));
    }

    // Target을 팔로우 한 사람
    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX + "/follower")
    public SearchResult<FollowMemberInfo> searchFollower(@RequestParam(name = "target") String target, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<FollowData> followDataPage = followDataDao.findAll(FollowDataSpec.followingIdEquals(target), PageRequest.of(page, SIZE));

        return getFollowMemberInfoSearchResult(page, followDataPage, followDataPage.stream().map(FollowData::getFollowerId));
    }

    private SearchResult<FollowMemberInfo> getFollowMemberInfoSearchResult(@RequestParam(name = "page", defaultValue = "0") final Integer page, final Page<FollowData> followDataPage, final Stream<String> stringStream) {
        final List<String> followingIds = stringStream.collect(Collectors.toList());

        final List<FollowMemberInfo> memberInfos = findMemberIdsService.findMembers(followingIds).stream()
                .map(FollowMemberInfo::from)
                .collect(Collectors.toList());

        return SearchResult.<FollowMemberInfo>builder()
                .totalPage(followDataPage.getTotalPages())
                .totalElements(followDataPage.getTotalElements())
                .page(page)
                .size(memberInfos.size())
                .values(memberInfos)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    public static class FollowMemberInfo {
        private final String loginId;
        private final String nickname;
        private final String profileImage;

        public static FollowMemberInfo from(final FindMemberIdsService.FindMemberResult findMemberResult) {
            return builder()
                    .loginId(findMemberResult.getMemberId())
                    .nickname(findMemberResult.getNickname())
                    .profileImage(findMemberResult.getProfileImage())
                    .build();
        }
    }
}
