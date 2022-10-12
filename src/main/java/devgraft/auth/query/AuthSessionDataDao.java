package devgraft.auth.query;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AuthSessionDataDao extends Repository<AuthSessionData, String> {
        Optional<AuthSessionData> findOne(Specification<AuthSessionData> spec);
}
