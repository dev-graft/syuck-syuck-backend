package devgraft.friend.domain;

public interface FriendEventSender {
    /** 친구요청 이벤트 발행 **/
    void postFriend(final Long friendRelationId, final String sender, final String receiver);
    void acceptPostFriend(final Long friendRelationId, final String sender, final String receiver);
    void cancelPostFriend(final String sender, final String receiver);
    void refusePostFriend(final String sender, final String receiver);
    void deleteFriend(final String issuer, final String receiver);
}
