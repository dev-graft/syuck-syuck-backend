package devgraft.follow.api;


import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.follow.app.CancelFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FOLLOW_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class CancelFollowApi {
    private final CancelFollowService cancelFollowService;

    @DeleteMapping(API_PREFIX + VERSION_1_PREFIX + FOLLOW_URL_PREFIX)
    public void cancelFollow(@Credentials final MemberCredentials memberCredentials, @RequestParam(name = "fId") String fId) {
        cancelFollowService.cancelFollow(memberCredentials.getMemberId(), fId);
    }
}
