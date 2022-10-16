package devgraft.follow.api;


import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.follow.app.UnfollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.UNFOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class UnfollowApi {
    private final UnfollowService unfollowService;

    @PostMapping(API_PREFIX + VERSION_1_PREFIX + UNFOLLOW_URL_PREFIX)
    public void Unfollow(@Credentials final MemberCredentials memberCredentials, @RequestParam(name = "target") String target) {
        unfollowService.Unfollow(memberCredentials.getMemberId(), target);
    }
}
