package devgraft.follow.query;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FollowData.class)
public class FollowData_ {
    public static volatile SingularAttribute<FollowData, Long> id;
    public static volatile SingularAttribute<FollowData, String> memberId;
    public static volatile SingularAttribute<FollowData, String> followingMemberId;
}
