package devgraft.auth.query;

import org.springframework.data.jpa.domain.Specification;

public class AuthSessionDataSpec {
    public static Specification<AuthSessionData> uniqIdEquals(final String uniqId) {
        return (root, query, cb) -> cb.equal(root.get(AuthSessionData_.uniqId), uniqId);
    }

    public static Specification<AuthSessionData> notBlock() {
        return (root, query, cb) -> cb.equal(root.get(AuthSessionData_.block), false);
    }

    public static Specification<AuthSessionData> memberIdEquals(final String memberId) {
        return (root, query, cb) -> cb.like(root.get(AuthSessionData_.memberId), memberId);
    }
}
