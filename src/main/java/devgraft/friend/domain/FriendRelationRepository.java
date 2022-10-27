package devgraft.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {
    List<FriendRelation> findFriendRelationBySenderOrReceiver(final String sender, final String receiver);
}
