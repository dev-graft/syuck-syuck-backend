package devgraft.friend.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.friend.app.AcceptFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class AcceptFriendApi {
    private final AcceptFriendService acceptFriendService;

    @PutMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX + "/posts/accept")
    public void acceptFriend(@Credentials MemberCredentials memberCredentials, @RequestParam(name = "target") Long friendRelationId) {
        acceptFriendService.acceptFriend(memberCredentials.getMemberId(), friendRelationId);
    }
}
