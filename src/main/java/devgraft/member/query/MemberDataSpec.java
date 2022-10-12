package devgraft.member.query;

import org.springframework.data.jpa.domain.Specification;

public class MemberDataSpec {
    public static Specification<MemberData> loggedIdEquals(final String loggedId) {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.memberId), loggedId);
    }

    public static Specification<MemberData> normalEquals() {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.status), MemberDataStatus.N);
    }

    public static Specification<MemberData> loggedIdLike(final String keyword) {
        return (root, query, cb) -> cb.like(root.get(MemberData_.memberId), keyword + "%");
    }

    public static Specification<MemberData> nicknameLike(final String keyword) {
        return (root, query, cb) -> cb.like(root.get(MemberData_.nickname), keyword + "%");
    }
}
