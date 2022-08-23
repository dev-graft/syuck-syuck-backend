package devgraft.member.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Member.class)
public class Member_ {
    public static volatile SingularAttribute<Member, Long> id;
    public static volatile SingularAttribute<Member, String> loginId;
    public static volatile SingularAttribute<Member, String> password;
    public static volatile SingularAttribute<Member, String> nickname;
    public static volatile SingularAttribute<Member, String> profileImage;
    public static volatile SingularAttribute<Member, String> stateMessage;
    public static volatile SingularAttribute<Member, MemberStatus> status;
}
