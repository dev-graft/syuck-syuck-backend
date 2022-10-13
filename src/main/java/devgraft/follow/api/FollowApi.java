package devgraft.follow.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.follow.app.AskFollowRequest;
import devgraft.follow.app.AskFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class FollowApi {
    private final AskFollowService askFollowService;
    // follow 요청(memberId)

    @PostMapping(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX)
    public void askFollow(@Credentials final MemberCredentials memberCredentials, @RequestBody final AskFollowRequest request) {
        askFollowService.askFollow(memberCredentials.getMemberId(), request);
    }
    // follow 취소(memberId)

    // following 목록 조회(memberId, page)

    // follower 목록 조회(memberId, page)
}
