package devgraft.member.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Getter
@Entity
public class Member extends BaseEntity {
    @EmbeddedId
    private MemberId id;
    @Embedded
    private Password password;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "state_message")
    private String stateMessage;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public boolean isLeave() {
        return status.isLeave();
    }

    public boolean isMatch(final MemberCryptoService memberCryptService, final String pwd) {
        final Password hashingPwd = memberCryptService.hashingPassword(pwd);
        return password.isMatch(hashingPwd.getValue());
    }

    public void updateProfile(final String nickname, final String stateMessage) {
        this.nickname = nickname;
        this.stateMessage = stateMessage;
    }

    public String getMemberId() {
        return id.getId();
    }
}
