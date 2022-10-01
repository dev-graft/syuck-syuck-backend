package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberPasswordService;
import devgraft.member.domain.MemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberPasswordService memberPasswordService;
    private final LoginRequestValidator loginRequestValidator;
    private final MemberRepository memberRepository;

    @Transactional()
    public void login(LoginRequest request, KeyPair keyPair) {
        List<ValidationError> errors = loginRequestValidator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors, "로그인 요청이 실패하였습니다.");
        Member member = MemberFindHelper.findMember(memberRepository, request.getLoginId());
        member.getLoggedIn().compareToPassword(memberPasswordService, request.getPassword(), keyPair);
    }

    /**
     * 해당 로직은 jwt를 알 필요가 없지
     * 권한은 추가되더라도 Member 도메인에 포함할 것이고, 이미 아이디 패스워드를 '인증'하는 로직이 해당 도메인에 있으니
     * '인가'도 Member에서 담당할 것
     * 그렇다면 필요한 내용은?
     *
     * 로그인을 통한 인증을 증명 후 인가 정보 accessToken, refreshToken을 반환할 것
     * 해당 내용은 도메인 서비스로 만들고, infra에서 JWT와 연결하여 만들어 볼 것
     *
     * 도메인 로직이라 트랜잭션 태우는 로직은 빼고 단순 생성만 해서 반환하는 쪽으로 하면
     * 흠 streamAllBy 를 사용해놔서 readOnly 밖에 안되는데?? db에 인가 정보를 넣어두는건 안되려나
     * 흠 아니지 streamAllBy를 바꿔보던지, 이전처럼 redis를 써보던지
     *
     * Id 설정을 잘못해준듯 싶다. idx는 딱히 향후 로직에도 쓰일 것 같지는 않고,
     */
}
