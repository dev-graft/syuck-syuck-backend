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

    /// TODO 지금 테스트 코드에서 키 전달이 당연히 RSA가 사용되듯이 되어 있는데
    ///      직접 호출은 로직에 변경이 발생했을 때 좋지 못한 것 같다.

    /**
     * 지금 회원가입과 로그인의 로직이 분리되어진 상태
     * 문제는 두 과정에 암호화/복호화 과정이 일어나는데
     * 암호화 모듈을 각자 따로 접근해서 사용하기 떄문에,
     * 회원가입의 암/복호화와 로그인의 암/복호화가 서로 다르게 구성할 수 있다.
     * 이를 해결이 필요하다.
     * 회원가입 프로세스(전달 받은 값의 널 체크 -> 전달받은 패스워드 복호화 -> 정규식 체크 -> 중복 아이디 확인 -> 복호화된 패스워드 해싱처리 -> 저장
     * 로그인 프로세스 (전달 받은 값의 널 체크 -> 전달받은 패스워드 복호화 -> 해싱처리 -> 아이디 조회 -> 조회된 엔티티의 패스워드와 비교 -> 끝
     *
     *
     * 그러면 member domain에 cryptService 인터페이스를 추가하고
     * 구현을 따로 시키게 하고
     *
     * 물론 이렇게 만들면 다른 암호화와 혼동되지 않게 구현클래스를 만들어 정리할 수 있지만
     * 마지막 문제는 기껏 만들어준 구현 클래스를 사용하지 않고 멋대로 개발할 수 있다.
     * 이를 막으려면 DecryptPasswordService 인터페이스의 함수의 반환 타입을 커스텀하는 것.
     * 흠 올바른 방법인지 생각 좀 하고 작성하자
     */
}
