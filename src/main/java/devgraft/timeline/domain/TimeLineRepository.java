package devgraft.timeline.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLineRepository extends JpaRepository<TimeLine, Long> {
}
