package devgraft.member.api;

import devgraft.member.app.MemberIds;
import devgraft.member.app.MembershipRequest;
import devgraft.member.app.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MembershipService membershipService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MemberIds membership(@RequestBody final MembershipRequest request) {
        return membershipService.membership(request);
    }

    // TODO 회원 아이디 중복 검사 (프론트에서 유저 입력마다 반복적으로 아이디 중복 체크를 한다고 하면 구현)
    // TODO 회원 조회
    // TODO 회원 프로필 수정
    // TODO 회원 탈퇴
    /*
     TODO 회원 기본 프로필 url 제공 / upload 도메인에서 관리?
     TODO 암호화 로직이 회원가입 및 로그인에만 구현할 예정이라 범위가 생각보다 좁아서, 초기화 요청 api를 겸용하는게 아니라 따로따로 회원가입 초기화 요청, 로그인 초기화 요청을 만드는게 나을지 판단
          별도로 구현하게 될 경우 회원가입 초기화 요청 결과에 암호화 값과 기본 프로필 url도 제공하는 등 다양한 방법으로 사용할 수 있다.
     */
}
