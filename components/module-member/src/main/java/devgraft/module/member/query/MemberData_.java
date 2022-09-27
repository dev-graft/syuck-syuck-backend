package devgraft.module.member.query;

import devgraft.module.member.domain.MemberStatus;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MemberData.class)
public class MemberData_ {
    public static volatile SingularAttribute<MemberData, String> memberId;
    public static volatile SingularAttribute<MemberData, String> nickname;
    public static volatile SingularAttribute<MemberData, String> profileImage;
    public static volatile SingularAttribute<MemberData, String> stateMessage;
    public static volatile SingularAttribute<MemberData, MemberStatus> status;
}
