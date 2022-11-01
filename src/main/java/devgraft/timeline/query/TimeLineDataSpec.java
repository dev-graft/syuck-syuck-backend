package devgraft.timeline.query;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;

import javax.persistence.criteria.Order;

public class TimeLineDataSpec {
    public static Specification<TimeLineData> idEquals(final Long id) {
        return (root, query, cb) -> cb.equal(root.get(TimeLineData_.id), id);
    }

    public static Specification<TimeLineData> memberIdEquals(final String memberId) {
        return (root, query, cb) -> cb.equal(root.get(TimeLineData_.memberId), memberId);
    }

    public static Specification<TimeLineData> orderByDESC() {
        return (root, query, cb) -> query.orderBy(cb.desc(root.get(TimeLineData_.id))).getRestriction();
    }
}
