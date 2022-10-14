package devgraft.follow.query;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(FollowData.class)
public class FollowData_ {
    public static volatile SingularAttribute<FollowData, Long> id;
    public static volatile SingularAttribute<FollowData, String> memberId;
    public static volatile SingularAttribute<FollowData, String> followingMemberId;
    public static volatile SingularAttribute<FollowData, LocalDateTime> createdAt;
    public static volatile SingularAttribute<FollowData, LocalDateTime> updatedAt;
}
