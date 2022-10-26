package devgraft.friend.infra;

import devgraft.friend.domain.FriendEventSender;
import devgraft.support.event.Event;
import devgraft.support.event.Events;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class FriendPushEventSender implements FriendEventSender {
    @Override
    public void postFriend(final Long friendRelationId, final String sender, final String receiver) {
        Events.raise(new PostFriendEvent(friendRelationId, sender, receiver));
    }

    @Override
    public void acceptPostFriend(final Long friendRelationId, final String sender, final String receiver) {
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

    @Getter
    public static class PostFriendEvent extends Event {
        private final Long fId;
        private final String sender;
        private final String receiver;

        public PostFriendEvent(final Long fId, final String sender, final String receiver) {
            super("Post-Friend");
            this.fId = fId;
            this.sender = sender;
            this.receiver = receiver;
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
}
