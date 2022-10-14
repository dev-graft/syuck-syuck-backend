package devgraft.follow.query;

import org.springframework.data.jpa.domain.Specification;

public class FollowDataSpec {
    public static Specification<FollowData> memberIdEquals(final String memberId) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("createdAt")));
            return cb.equal(root.get(FollowData_.memberId), memberId);
        };
    }

    public static Specification<FollowData> followingMemberIdEquals(final String followingMemberId) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("createdAt")));
            return cb.equal(root.get(FollowData_.followingMemberId), followingMemberId);
        };
    }
}
