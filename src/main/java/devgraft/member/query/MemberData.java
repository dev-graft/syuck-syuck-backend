package devgraft.member.query;

import devgraft.member.domain.MemberStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
@Getter
public class MemberData {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "logged_id")
    private String loggedId;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "state_message")
    private String stateMessage;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MemberStatus status;
}
