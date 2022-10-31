package devgraft.friend.infra;

import devgraft.client.member.MemberClient;
import devgraft.client.member.MemberClient.FindMemberResult;
import devgraft.friend.domain.FriendEventSender;
import devgraft.support.event.EventCode;
import devgraft.support.event.Events;
import devgraft.support.event.push.PushEventInterface;
import devgraft.support.event.timeline.TimeLineEventInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * sender receiver 햇갈림.
 **/
@RequiredArgsConstructor
@Component
public class FriendPushEventSender implements FriendEventSender {
    private final MemberClient memberClient;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum FriendEventCode implements EventCode {
        POST_SEND("POST-SEND", "[%s]님에게 친구를 요청했습니다."),
        POST_RECEIVE("POST-RECEIVE", "[%s]님에게 친구요청이 왔습니다."),
        ACCEPT_POST_SEND("ACCEPT-POST-SEND", "[%s]님의 친구요청을 수락했습니다."),
        ACCEPT_POST_RECEIVE("ACCEPT-POST-RECEIVE", "[%s]님이 친구요청을 수락했습니다."),
        REFUSE_POST("REFUSE-POST", "[%s]님의 친구요청을 거절했습니다."),
        REFUSE_RECEIVE("REFUSE-RECEIVE", "[%s]님이 친구요청을 거절했습니다."),
        CANCEL_POST("CANCEL-POST", "[%s]님에게 요청한 친구요청을 취소했습니다."),
        DELETE_SEND("DELETE-SEND", "[%s]님과 친구관계를 취소했습니다."),
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
    public void postFriend(final Long friendRelationId, final String poster, final String target) {
        final FindMemberResult senderInfo = memberClient.findMember(poster);
        final FindMemberResult receiverInfo = memberClient.findMember(target);
        Events.raise(FriendSenderEvent.of(FriendEventCode.POST_SEND, poster, receiverInfo.getNickname()));
        Events.raise(FriendReceiverEvent.of(FriendEventCode.POST_RECEIVE, target, senderInfo.getNickname()));
    }

    @Override
    public void acceptPostFriend(final Long friendRelationId, final String accepter, final String target) {
        final FindMemberResult accepterInfo = memberClient.findMember(accepter);
        final FindMemberResult receiverInfo = memberClient.findMember(target);
        Events.raise(FriendSenderEvent.of(FriendEventCode.ACCEPT_POST_SEND, accepter, receiverInfo.getNickname()));
        Events.raise(FriendReceiverEvent.of(FriendEventCode.ACCEPT_POST_RECEIVE, target, accepterInfo.getNickname()));
    }

    @Override
    public void cancelPostFriend(final String canceler, final String target) {
        final FindMemberResult receiverInfo = memberClient.findMember(target);
        Events.raise(FriendSenderEvent.of(FriendEventCode.CANCEL_POST, canceler, receiverInfo.getNickname()));
    }

    @Override
    public void refusePostFriend(final String requester, final String target) {
        final FindMemberResult requestInfo = memberClient.findMember(requester);
        final FindMemberResult targetInfo = memberClient.findMember(target);
        Events.raise(FriendSenderEvent.of(FriendEventCode.REFUSE_POST, requester, targetInfo.getNickname()));
        Events.raise(FriendReceiverEvent.of(FriendEventCode.REFUSE_RECEIVE, target, requestInfo.getNickname()));
    }

    @Override
    public void deleteFriend(final String requester, final String receiver) {
        final FindMemberResult senderInfo = memberClient.findMember(requester);
        final FindMemberResult receiverInfo = memberClient.findMember(receiver);
        Events.raise(FriendSenderEvent.of(FriendEventCode.DELETE_SEND, requester, receiverInfo.getNickname()));
        Events.raise(FriendReceiverEvent.of(FriendEventCode.DELETE_RECEIVE, receiver, senderInfo.getNickname()));
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class FriendSenderEvent implements TimeLineEventInterface {
        private final FriendEventCode eventCode;
        private final String memberId;
        private final String content;

        public static FriendSenderEvent of(final FriendEventCode friendEventCode, final String memberId, final String messageArg) {
            return new FriendSenderEvent(friendEventCode, memberId, String.format(friendEventCode.getMessageFormat(), messageArg));
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class FriendReceiverEvent implements PushEventInterface, TimeLineEventInterface {
        private final FriendEventCode eventCode;
        private final String memberId;
        private final String title;
        private final String content;

        public static FriendReceiverEvent of(final FriendEventCode friendEventCode, final String memberId, final String title, final String messageArg) {
            return new FriendReceiverEvent(friendEventCode, memberId, title, String.format(friendEventCode.getMessageFormat(), messageArg));
        }

        public static FriendReceiverEvent of(final FriendEventCode friendEventCode, final String memberId, final String messageArg) {
            return new FriendReceiverEvent(friendEventCode, memberId, "친구 알림", String.format(friendEventCode.getMessageFormat(), messageArg));
        }
    }
}