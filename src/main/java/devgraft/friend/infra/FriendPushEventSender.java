package devgraft.friend.infra;

import devgraft.client.member.MemberClient;
import devgraft.client.member.MemberClient.FindMemberResult;
import devgraft.friend.domain.FriendEventSender;
import devgraft.support.event.EventCode;
import devgraft.support.event.Events;
import devgraft.support.event.push.PushEventInterface;
import devgraft.support.event.TimeLineEventInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FriendPushEventSender implements FriendEventSender {
    private final MemberClient memberClient;

    @Getter @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum FriendEventCode implements EventCode {
        POST_SEND("POST-SEND", "[%s]님에게 친구를 요청했습니다."),
        POST_RECEIVE("POST-RECEIVE", "[%s]님에게 친구요청이 왔습니다."),
        ACCEPT_POST_SEND("ACCEPT-POST-SEND", "[%s]님이 친구요청을 수락했습니다."),
        ACCEPT_POST_RECEIVE("ACCEPT-POST-RECEIVE", "[%s]님의 친구요청을 수락했습니다."),
        REFUSE_POST("REFUSE-POST", "[%s]님의 친구요청을 거절했습니다."),
        DELETE_SEND("DELETE-SEND", "[%s]님과 친구관계를 취소했습니다."),
        CANCEL_POST("CANCEL-POST", "[%s]님에게 요청한 친구요청을 취소했습니다."),
        DELETE_RECEIVE("DELETE-RECEIVE", "[%s]님이 친구관계를 취소했습니다."),
        ;
        private static final String TAG = "FRIEND";
        private final String code;
        private final String messageFormat;

        @Override
        public String getTag() {
            return TAG;
        }
    }

    @Override
    public void postFriend(final Long friendRelationId, final String sender, final String receiver) {
        final FindMemberResult senderInfo = memberClient.findMember(sender);
        final FindMemberResult receiverInfo = memberClient.findMember(receiver);

        // receiver 님에게 친구를 요청했습니다. (타임라인만 체크하면 될 것 같은데...)
        Events.raise(FriendEvent.of(FriendEventCode.POST_SEND, sender, "친구요청 알림",  receiverInfo.getNickname()));
        // sender 님에게 친구요청이 왔습니다. (타임라인, Push 둘다)
        Events.raise(FriendEvent.of(FriendEventCode.POST_RECEIVE, receiver, "친구요청 알림", senderInfo.getNickname()));
    }

    @Override
    public void acceptPostFriend(final Long friendRelationId, final String sender, final String receiver) {
        // sender 님의 친구요청을 수락했습니다.
        // receiver 님이 친구요청을 수락했습니다.
    }

    @Override
    public void cancelPostFriend(final String sender, final String receiver) {
        // TODO 추가 예정
    }

    @Override
    public void refusePostFriend(final String sender, final String receiver) {
        // TODO 추가 예정
    }

    @Override
    public void deleteFriend(final String issuer, final String receiver) {
        // TODO 추가 예정
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class FriendEvent implements PushEventInterface, TimeLineEventInterface {
        private final FriendEventCode eventCode;
        private final String memberId;
        private final String title;
        private final String content;

        public static FriendEvent of(final FriendEventCode friendEventCode, final String memberId, final String title, final String messageArg) {
            return new FriendEvent(friendEventCode, memberId, title, String.format(friendEventCode.getMessageFormat(), messageArg));
        }
    }
}
