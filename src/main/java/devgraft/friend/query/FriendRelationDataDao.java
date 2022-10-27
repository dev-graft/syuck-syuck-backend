package devgraft.friend.query;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface FriendRelationDataDao extends Repository<FriendRelationData, Long> {
    Optional<FriendRelationData> findOne(Specification<FriendRelationData> spec);
    Page<FriendRelationData> findAll(Specification<FriendRelationData> spec, Pageable pageable);
}
