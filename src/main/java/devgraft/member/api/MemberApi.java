package devgraft.member.api;

import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberQueryService;
import devgraft.support.exception.NoContentException;
import devgraft.support.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberQueryService memberQueryService;
    private final MembershipService membershipService;

    @PostMapping
    public CommonResult membership(@RequestBody final MembershipRequest request) {
        membershipService.membership(request);
        return CommonResult.success(HttpStatus.CREATED);
    }

    @GetMapping("{loginId}")
    public MemberProfileGetResult getMemberProfile(@PathVariable(name = "loginId") final String loginId) {
        final MemberData memberData = memberQueryService.getMemberData(loginId)
                .orElseThrow(() -> new NoContentException("존재하지 않는 회원입니다."));

        return MemberProfileGetResult.builder()
                .loginId(memberData.getLoginId())
                .nickname(memberData.getNickname())
                .profileImage(memberData.getProfileImage())
                .stateMessage(memberData.getStateMessage())
                .createdAt(memberData.getCreatedAt())
                .build();
    }

    // TODO 회원 아이디 중복 검사 (프론트에서 유저 입력마다 반복적으로 아이디 중복 체크를 한다고 하면 구현)
    // TODO 회원 조회
    // TODO 회원 프로필 수정
    // TODO 회원 탈퇴
    /*
     TODO 회원 기본 프로필 url 제공 / upload 도메인에서 관리?
     TODO 암호화 로직이 회원가입 및 로그인에만 구현할 예정이라 범위가 생각보다 좁아서, 초기화 요청 api를 겸용하는게 아니라 따로따로 회원가입 초기화 요청, 로그인 초기화 요청을 만드는게 나을지 판단
          별도로 구현하게 될 경우 회원가입 초기화 요청 결과에 암호화 값과 기본 프로필 url도 제공하는 등 다양한 방법으로 사용할 수 있다.
          -> 대칭키 발급 api를 어디에 두어야하는가 고민. 회원가입은 member 도메인의 표현영역이고, 로그인은 auth 도메인의 표현영역에 둘 것.
     */

    /**
     * 로그인
     * - 1.회원 아이디 기준 조회
     * - 2.패스워드 비교 -> 도메인 로직
     *
     * 패스워드 변경
     * - 1.회원 아이디 기준 조회
     * - 2.패스워드 비교
     *
     * 2차인증
     * - 1.회원 아이디 기준 조회
     * - 2.패스워드 비교
     *
     * 회원가입
     * - 1.대칭키로 전달받은 패스워드 복호화
     * - 2.정규식 검증
     * - 3.패스워드 해싱 후 DB에 저장
     *
     * 회원가입의 과정에서 발생하는 해싱으로 인해 로그인, 패스워드 변경, 2차인증 등 [패스워드 비교] 과정에 해싱이 필요하다
     *
     * 패스워드 비교
     * - 1. 비교 패스워드, 대상 패스워드, 대칭키
     * - 2. 비교 패스워드를 대칭키로 복호화
     * - 3. 비교 패스워드를 해싱
     * - 4. 패스워드 비교 후 결과 리턴
     */
}
