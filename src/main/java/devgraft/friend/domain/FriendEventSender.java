package devgraft.friend.domain;

public interface FriendEventSender {

    /**
     * 친구 요청
     * @param poster 친구요청자
     * @param target 대상
     */
    void postFriend(final Long friendRelationId, final String poster, final String target);

    /**
     * 친구 요청 수락
     * @param accepter 친구 요청 수락한 사람
     * @param target 친구 요청자
     */
    void acceptPostFriend(final Long friendRelationId, final String accepter, final String target);

    /**
     * 친구 요청 취소
     * @param canceler 친구 요청 취소한 사람
     * @param target 대상
     */
    void cancelPostFriend(final String canceler, final String target);

    /**
     * 친구 요청 거절
     * @param requester 친구 요청 거절한 사람
     * @param target 대상
     */
    void refusePostFriend(final String requester, final String target);

    /**
     * 친구 삭제
     * @param requester 요청자
     * @param receiver 받은 사람
     */
    void deleteFriend(final String requester, final String receiver);
}
