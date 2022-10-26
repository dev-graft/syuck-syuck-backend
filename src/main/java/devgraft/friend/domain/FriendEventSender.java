package devgraft.friend.domain;

public interface FriendEventSender {
    void postFriend(final Long friendRelationId, final String sender, final String receiver);
    void acceptPostFriend(final Long friendRelationId, final String sender, final String receiver);
    void cancelPostFriend(final String sender, final String receiver);
    void refusePostFriend(final String sender, final String receiver);
}
