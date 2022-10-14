package devgraft.follow.domain;

public interface FollowEventSender {
    void askFollow(final String memberId, final String followingId);
    void cancelFollow(final String memberId, final String cancelFollowId);
}
