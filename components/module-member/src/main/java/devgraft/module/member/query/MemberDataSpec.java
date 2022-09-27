package devgraft.module.member.query;

import devgraft.module.member.domain.MemberStatus;
import org.springframework.data.jpa.domain.Specification;

public class MemberDataSpec {
    public static Specification<MemberData> loggedIdEquals(final String loggedId) {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.memberId), loggedId);
    }

    public static Specification<MemberData> normalEquals() {
        return (root, query, cb) -> cb.equal(root.get(MemberData_.status), MemberStatus.N);
    }

    public static Specification<MemberData> nameLike(final String keyword) {
        return (root, query, cb) -> cb.like(root.get(MemberData_.nickname), keyword + "%");
    }
}
