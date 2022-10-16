package devgraft.follow.query;

import org.springframework.data.jpa.domain.Specification;

public class FollowDataSpec {
    public static Specification<FollowData> followerIdEquals(final String followerId) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("createdAt")));
            return cb.equal(root.get(FollowData_.followerId), followerId);
        };
    }

    public static Specification<FollowData> followingIdEquals(final String followingId) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("createdAt")));
            return cb.equal(root.get(FollowData_.followingId), followingId);
        };
    }
}
