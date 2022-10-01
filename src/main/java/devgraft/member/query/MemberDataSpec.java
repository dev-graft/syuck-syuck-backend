package devgraft.member.query;

import org.springframework.data.jpa.domain.Specification;

public class MemberDataSpec {
    public static Specification<MemberData> memberIdEquals(final Long memberId) {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.id), memberId);
    }

    public static Specification<MemberData> loggedIdEquals(final String loggedId) {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.loggedId), loggedId);
    }

    public static Specification<MemberData> normalEquals() {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.status), MemberStatus.N);
    }

    public static Specification<MemberData> nameLike(final String keyword) {
        return (root, query, cb) -> cb.like(root.get(MemberData_.nickname), keyword + "%");
    }
}
