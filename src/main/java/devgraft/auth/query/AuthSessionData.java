package devgraft.auth.query;

import devgraft.auth.domain.AccessStatusType;
import devgraft.auth.domain.DeviceOSType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auth_session")
@Entity
@Getter
public class AuthSessionData {
    @Id
    @Column(name = "uniq_id")
    private String uniqId;
    @Column(name = "member_id")
    private String memberId;
    @Column(name = "version")
    private String version;
    @Column(name = "push_token")
    private String pushToken;
    @Column(name = "os")
    private DeviceOSType os;
    @Column(name = "device_name")
    private String deviceName;
    @Column(name = "connect")
    private boolean connect;
    private AccessStatusType accessStatus;
    @Column(name = "block")
    private boolean block;
}
