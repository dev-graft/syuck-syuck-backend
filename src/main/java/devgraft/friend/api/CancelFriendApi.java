package devgraft.friend.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.friend.app.CancelFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class CancelFriendApi {
    private final CancelFriendService cancelFriendService;

    @PutMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/cancel")
    public void cancelFriend(@Credentials final MemberCredentials memberCredentials, @RequestParam(name = "target") final Long friendRelationId) {
        cancelFriendService.cancelFriend(memberCredentials.getMemberId(), friendRelationId);
    }
}
