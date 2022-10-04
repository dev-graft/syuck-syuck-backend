package devgraft.auth.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String memberId; // 포링? ㄴㄴ 걸어봐야다 서비스 분리하면 어떻게 할껀데
    private String version;
    private String pushToken;
    private String os;
    private String deviceName;
    private boolean block;
}

