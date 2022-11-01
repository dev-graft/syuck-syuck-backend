package devgraft.timeline.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

public interface TimeLineDataDao extends Repository<TimeLineData, Long> {
    Page<TimeLineData> findAll(Specification<TimeLineData> spec, Pageable pageable);

}
