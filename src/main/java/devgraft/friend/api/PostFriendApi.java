package devgraft.friend.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.friend.app.PostFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class PostFriendApi {
    private final PostFriendService postFriendService;

    @PostMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
    public void postFriendApi(@Credentials MemberCredentials memberCredentials, @RequestParam String target) {
        postFriendService.postFriend(memberCredentials.getMemberId(), target);
    }
}
