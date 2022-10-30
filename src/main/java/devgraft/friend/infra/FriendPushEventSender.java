package devgraft.friend.infra;

import devgraft.friend.domain.FriendEventSender;
import devgraft.support.event.Event;
import devgraft.support.event.EventCode;
import devgraft.support.event.Events;
import devgraft.support.event.PushEventInterface;
import devgraft.support.event.TimeLineEventInterface;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class FriendPushEventSender implements FriendEventSender {


    @Getter @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum FriendEventCode implements EventCode {
        POST_SEND("post-send", "{name}님에게 친구를 요청했습니다."),
        POST_RECEIVE("post-receive", "{name}님에게 친구요청이 왔습니다."),
        ;

        private static final String TAG = "friend";
        private final String code;
        private final String messageFormat;

        @Override
        public String getTag() {
            return TAG;
        }
    }

    /**
     * 이벤트 클래스를 타임라인과 푸쉬용을 상속받아서 리스너들이 읽도록 하는 방법이 있고
     * 아무것도 의존하지 않은 PostFriendEvent를 리스너들이 긁어가는 방법이 있다.
     *
     * 흠 후자로 처리하면 각 리스너들이 해당 도메인에 대한 이해도와 의존을 갖어야하므로 복잡해질 것 같다.
     * 차라리 발생할 이벤트가 필요한 리스너와 연관된 클래스를 의존하는 것이 좋을듯듯     */



    @Override
    public void postFriend(final Long friendRelationId, final String sender, final String receiver) {
        // receiver 님에게 친구를 요청했습니다.
        Events.raise(FriendEvent.create("post-friend", sender,   "님에게 친구를 요청했습니다."));
        // sender 님에게 친구요청이 왔습니다.
        Events.raise(FriendEvent.create("post-friend", receiver, "님에게 친구요청이 왔습니다."));
    }

    @Override
    public void acceptPostFriend(final Long friendRelationId, final String sender, final String receiver) {
        // sender 님의 친구요청을 수락했습니다.
        // receiver 님이 친구요청을 수락했습니다.
        Events.raise(new AcceptPostFriendEvent(friendRelationId, sender, receiver));
    }

    @Override
    public void cancelPostFriend(final String sender, final String receiver) {
        Events.raise(new CancelPostFriendEvent(sender, receiver));
    }

    @Override
    public void refusePostFriend(final String sender, final String receiver) {
        Events.raise(new RefusePostFriendEvent(sender, receiver));
    }

    @Override
    public void deleteFriend(final String issuer, final String receiver) {
        Events.raise(new DeleteFriendEvent(issuer, receiver));
    }

    @Getter
    public static class PostFriendEvent extends Event implements TimeLineEventInterface {
        private final Long fId;
        private final String sender;
        private final String receiver;

        public PostFriendEvent(final Long fId, final String sender, final String receiver) {
            super("Post-Friend");
            this.fId = fId;
            this.sender = sender;
            this.receiver = receiver;
        }

        @Override
        public String getMemberId() {
            return sender;
        }

        @Override
        public String getMessage() {
            return receiver + "님에게 친구요청을 신청했습니다.";
        }
    }


    @Getter
    public static class FriendEvent extends Event implements PushEventInterface {
        private final String memberId;
        private final String message;

        private FriendEvent(final String tag, final String memberId, final String message) {
            super(tag);
            this.memberId = memberId;
            this.message = message;
        }

        public static FriendEvent create(final String tag, final String memberId, final String message) {
            return new FriendEvent(tag,memberId, message);
        }

        @Override
        public String getMemberId() {
            return memberId;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    @Getter
    public static class AcceptPostFriendEvent extends Event {
        private final Long fId;
        private final String sender;
        private final String receiver;

        public AcceptPostFriendEvent(final Long fId, final String sender, final String receiver) {
            super("Accept-Post-Friend");
            this.fId = fId;
            this.sender = sender;
            this.receiver = receiver;
        }
    }

    @Getter
    public static class CancelPostFriendEvent extends Event {
        private final String sender;
        private final String receiver;

        public CancelPostFriendEvent(final String sender, final String receiver) {
            super("Cancel-Post-Friend");
            this.sender = sender;
            this.receiver = receiver;
        }
    }

    @Getter
    public static class RefusePostFriendEvent extends Event {
        private final String sender;
        private final String receiver;

        public RefusePostFriendEvent(final String sender, final String receiver) {
            super("Refuse-Post-Friend");
            this.sender = sender;
            this.receiver = receiver;
        }
    }

    @Getter
    public static class DeleteFriendEvent extends Event {
        private final String issuer;
        private final String receiver;

        public DeleteFriendEvent(final String issuer, final String receiver) {
            super("Delete-Friend");
            this.issuer = issuer;
            this.receiver = receiver;
        }
    }
}
