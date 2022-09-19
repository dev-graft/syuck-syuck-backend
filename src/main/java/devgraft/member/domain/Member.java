package devgraft.member.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.KeyPair;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "loggedId", column = @Column(table = "member", name = "logged_id", unique = true, nullable = false)),
            @AttributeOverride(name = "password", column = @Column(table = "member", name = "password"))
    })
    private LoggedIn loggedIn;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "state_message")
    private String stateMessage;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MemberStatus status;

    void setId(final Long idx) {
        this.id = idx;
    }

    @Builder
    private Member(final Long id, final LoggedIn loggedIn, final String nickname, final String profileImage, final String stateMessage, final MemberStatus status) {
        this.id = id;
        this.loggedIn = loggedIn;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.stateMessage = stateMessage;
        this.status = status;
    }

    public static Member of(final LoggedIn loggedIn, final String nickname, final String profileImage) {
        return new Member(null, loggedIn, nickname, profileImage, "", MemberStatus.N);
    }

    public boolean isLeave() {
        return this.status.isLeave();
    }

    public void setProfile(final String nickname, final String stateMessage, final String profileImage) {
        this.nickname = StringUtils.hasText(nickname) ? nickname : this.nickname;
        this.stateMessage = StringUtils.hasText(stateMessage) ? stateMessage : this.stateMessage;
        this.profileImage = StringUtils.hasText(profileImage) ? profileImage : this.profileImage;
    }

    public void compareToPassword(final MemberPasswordService decryptPasswordService, final String encryptedPwd, final KeyPair keyPair) throws RuntimeException {
        try {
            final String decryptPwd = decryptPasswordService.decryptPassword(encryptedPwd, keyPair);
            final String hash = decryptPasswordService.hashingPassword(decryptPwd);
            if (!Objects.equals(loggedIn.getPassword(), hash)) {
                throw new RuntimeException();
            }
        } catch (final Exception e) {
            throw new NotCorrectPasswordException();
        }
    }
}
