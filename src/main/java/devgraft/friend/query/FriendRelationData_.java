package devgraft.friend.query;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FriendRelationData.class)
public class FriendRelationData_ {
    public static volatile SingularAttribute<FriendRelationData, Long> id;
    public static volatile SingularAttribute<FriendRelationData, Boolean> areFriends;
    public static volatile SingularAttribute<FriendRelationData, String> sender;
    public static volatile SingularAttribute<FriendRelationData, String> receiver;
}
