package devgraft.member.query;

import org.springframework.data.jpa.domain.Specification;

public class MemberDataSpec {
    public static Specification<MemberData> memberIdEquals(final Long memberId) {
        return (root, query, cb) -> cb.equal(root.<Long>get("member_id"), memberId);
    }

    public static Specification<MemberData> loggedIdEquals(final String loggedId) {
        return (root, query, cb) -> cb.equal(root.<Long>get("logged_id"), loggedId);
    }

    public static Specification<MemberData> normalEquals() {
        return (root, query, cb) -> cb.equal(root.<String>get("status"), "N");
    }

    public static Specification<MemberData> nameLike(final String keyword) {
        return (root, query, cb) -> cb.like(root.<String>get("nickname"), keyword + "%");
    }
}
