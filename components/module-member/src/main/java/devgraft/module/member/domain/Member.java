package devgraft.module.member.domain;

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
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "state_message")
    private String stateMessage;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member of(final MemberId memberId, final Password password, final String nickname,
                            final String profileImage, final String stateMessage, final MemberStatus memberStatus) {
        return builder()
                .id(memberId)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .stateMessage(stateMessage)
                .status(memberStatus)
                .build();
    }

    public boolean isLeave() {
        return status.isLeave();
    }

    public boolean match(final MemberCryptService memberCryptService, final String pwd) {
        final Password hashingPwd = memberCryptService.hashingPassword(pwd);
        return password.match(hashingPwd.getValue());
    }
}
