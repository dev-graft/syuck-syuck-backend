package devgraft.member.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_id", unique = true)
    private String loginId;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "state_message")
    private String stateMessage;
    @Column(name = "status")
    private MemberStatus status;
    void setId(final Long idx) {
        this.id = idx;
    }
    @Builder
    private Member(final Long id, final String loginId, final String password, final String nickname, final String profileImage, final String stateMessage, final MemberStatus status) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.stateMessage = stateMessage;
        this.status = status;
    }
    public static Member of(final String loginId, final String password, final String nickname, final String profileImage, final String stateMessage) {
        return new Member(null, loginId, password, nickname, profileImage, stateMessage, MemberStatus.N);
    }
    public boolean isLeave() {
        return this.status.isLeave();
    }

    public void setProfile(final String nickname, final String stateMessage, final String profileImage) {
        this.nickname = StringUtils.hasText(nickname) ? nickname : this.nickname;
        this.stateMessage = StringUtils.hasText(stateMessage) ? stateMessage : this.stateMessage;
        this.profileImage = StringUtils.hasText(profileImage) ? profileImage : this.profileImage;
    }


}
