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
    public void acceptFriend(final Long friendRelationId, final String sender, final String receiver) {
        Events.raise(new AcceptFriendEvent(friendRelationId, sender, receiver));
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
    public static class AcceptFriendEvent extends Event {
        private final Long fId;
        private final String sender;
        private final String receiver;

        public AcceptFriendEvent(final Long fId, final String sender, final String receiver) {
            super("Accept-Friend");
            this.fId = fId;
            this.sender = sender;
            this.receiver = receiver;
        }
    }
}
