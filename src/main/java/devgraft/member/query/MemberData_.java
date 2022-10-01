package devgraft.member.query;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MemberData.class)
public class MemberData_ {
    public static volatile SingularAttribute<MemberData,Long> id;
    public static volatile SingularAttribute<MemberData, String> loggedId;
    public static volatile SingularAttribute<MemberData, String> nickname;
    public static volatile SingularAttribute<MemberData, String> profileImage;
    public static volatile SingularAttribute<MemberData, String> stateMessage;
    public static volatile SingularAttribute<MemberData, MemberStatus> status;
}
