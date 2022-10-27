package devgraft.auth.query;

import devgraft.auth.domain.AccessStatusType;
import devgraft.auth.domain.DeviceOSType;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AuthSessionData.class)
public class AuthSessionData_ {
    public static volatile SingularAttribute<AuthSessionData, String> uniqId;
    public static volatile SingularAttribute<AuthSessionData, String> memberId;
    public static volatile SingularAttribute<AuthSessionData, String> version;
    public static volatile SingularAttribute<AuthSessionData, String> pushToken;
    public static volatile SingularAttribute<AuthSessionData, DeviceOSType> os;
    public static volatile SingularAttribute<AuthSessionData, String> deviceName;
    public static volatile SingularAttribute<AuthSessionData, Boolean> connect;
    public static volatile SingularAttribute<AuthSessionData, AccessStatusType> accessStatus;
    public static volatile SingularAttribute<AuthSessionData, Boolean> block;
}
