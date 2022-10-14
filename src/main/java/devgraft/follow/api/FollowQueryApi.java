package devgraft.follow.api;

import devgraft.common.SearchResult;
import devgraft.follow.query.FollowData;
import devgraft.follow.query.FollowDataDao;
import devgraft.follow.query.FollowDataSpec;
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
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class FollowQueryApi {
    private static final int SIZE = 20;
    private final FollowDataDao followDataDao;

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX + "/following")
    public SearchResult<FollowInfo> searchFollowing(@RequestParam(name = "memberId") String memberId, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<FollowData> followDataPage = followDataDao.findAll(FollowDataSpec.memberIdEquals(memberId), PageRequest.of(page, SIZE));

        final List<FollowInfo> followInfos = followDataPage.stream()
                .map(FollowInfo::from)
                .collect(Collectors.toList());

        return SearchResult.<FollowInfo>builder()
                .totalPage(followDataPage.getTotalPages())
                .totalElements(followDataPage.getTotalElements())
                .page(page)
                .size(followInfos.size())
                .values(followInfos)
                .build();
    }

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX + "/follower")
    public SearchResult<FollowInfo> searchFollower(@RequestParam(name = "memberId") String memberId, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        final Page<FollowData> followDataPage = followDataDao.findAll(FollowDataSpec.followingMemberIdEquals(memberId), PageRequest.of(page, SIZE));
        final List<FollowInfo> followInfos = followDataPage.stream()
                .map(FollowInfo::from)
                .collect(Collectors.toList());

        return SearchResult.<FollowInfo>builder()
                .totalPage(followDataPage.getTotalPages())
                .totalElements(followDataPage.getTotalElements())
                .page(page)
                .size(followInfos.size())
                .values(followInfos)
                .build();
    }
    // TODO 그냥 팔로우 목록 조회된 사용자들의 정보를 긁어와서 보여주는 형식으로 변경
    @Getter
    public static class FollowInfo {
        private final String followMemberId;

        private FollowInfo(final String followMemberId) {
            this.followMemberId = followMemberId;
        }

        public static FollowInfo from(final FollowData followData) {
            return new FollowInfo(followData.getFollowingMemberId());
        }
    }
}
