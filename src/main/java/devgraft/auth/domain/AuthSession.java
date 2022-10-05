package devgraft.auth.domain;

import devgraft.support.jpa.BaseEntity;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auth_session")
@Entity
@Getter
public class AuthSession extends BaseEntity {
    @Id
    private String uniqId;
    @Column(name = "member_id", nullable = false)
    private String memberId;
    private String version;
    private String pushToken;
    private String os;
    private String deviceName;
    private boolean block;
}

