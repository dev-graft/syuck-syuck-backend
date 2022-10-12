package devgraft.member.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberDataDao extends Repository<MemberData, String> {
    Optional<MemberData> findOne(Specification<MemberData> spec);
    Page<MemberData> findAll(Specification<MemberData> spec, Pageable pageable);
}
