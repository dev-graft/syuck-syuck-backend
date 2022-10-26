package devgraft.friend.domain;

public interface FriendEventSender {
    void postFriend(final Long friendRelationId, final String sender, final String receiver);
    void acceptFriend(final Long friendRelationId, final String sender, final String receiver);
    void cancelFriend(final String sender, final String receiver);
}
