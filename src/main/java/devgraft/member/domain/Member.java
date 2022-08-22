package devgraft.member.domain;

import devgraft.support.jpa.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String id;
    private String password;
    private String nickname;
    private String profileImage;
    private String stateMessage;
    private MemberStatus status;

    void setIdx(final Long idx) {
        this.idx = idx;
    }

    @Builder
    public Member(Long idx, String id, String password, String nickname, String profileImage, String stateMessage, MemberStatus status) {
        this.idx = idx;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.stateMessage = stateMessage;
        this.status = status;
    }

    public static Member of(final String id, final String password, final String nickname, final String profileImage, final String stateMessage) {
        return new Member(null, id, password, nickname, profileImage, stateMessage, MemberStatus.N);
    }
}
