package devgraft.friend.query;

import org.springframework.data.jpa.domain.Specification;

public class FriendRelationDataSpec {
    public static Specification<FriendRelationData> idEquals(final String id) {
        return (root, query, cb) -> cb.equal(root.get(FriendRelationData_.id), id);
    }

    public static Specification<FriendRelationData> areFriends(final Boolean areFriends) {
        return (root, query, cb) -> cb.equal(root.get(FriendRelationData_.areFriends), areFriends);
    }

    public static Specification<FriendRelationData> senderEquals(final String sender) {
        return (root, query, cb) -> cb.equal(root.get(FriendRelationData_.sender), sender);
    }

    public static Specification<FriendRelationData> receiverEquals(final String receiver) {
        return (root, query, cb) -> cb.equal(root.get(FriendRelationData_.receiver), receiver);
    }
}
