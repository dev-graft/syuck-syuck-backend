package devgraft.friend.api;

import devgraft.common.credential.Credentials;
import devgraft.common.credential.MemberCredentials;
import devgraft.friend.app.DeleteFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.FRIEND_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@RequiredArgsConstructor
@RestController
public class DeleteFriendApi {
    private final DeleteFriendService deleteFriendService;

    @DeleteMapping(API_PREFIX + VERSION_1_PREFIX + FRIEND_URL_PREFIX)
    public void deleteFriend(@Credentials MemberCredentials memberCredentials, @RequestParam(name = "target") Long friendRelationId) {
        deleteFriendService.deleteFriend(memberCredentials.getMemberId(), friendRelationId);
    }
}
