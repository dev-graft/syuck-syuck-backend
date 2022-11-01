package devgraft.timeline.query;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(TimeLineData.class)
public class TimeLineData_ {
    public static volatile SingularAttribute<TimeLineData, Long> id;
    public static volatile SingularAttribute<TimeLineData, String> tag;
    public static volatile SingularAttribute<TimeLineData, String> code;
    public static volatile SingularAttribute<TimeLineData, String> memberId;
    public static volatile SingularAttribute<TimeLineData, String> content;
    public static volatile SingularAttribute<TimeLineData, LocalDateTime> createAt;
    public static volatile SingularAttribute<TimeLineData, LocalDateTime> updateAt;
}
