package devgraft.follow.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface FollowDataDao extends Repository<FollowData, Long> {
    Optional<FollowData> findOne(Specification<FollowData> spec);
    Page<FollowData> findAll(Specification<FollowData> spec, Pageable pageable);
}