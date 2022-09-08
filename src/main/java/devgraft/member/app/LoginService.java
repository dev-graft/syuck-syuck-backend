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
    @Transactional(readOnly = true)
    public void login(LoginRequest request, KeyPair keyPair) {
        List<ValidationError> errors = loginRequestValidator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors, "로그인 요청이 실패하였습니다.");
        Member member = MemberFindHelper.findMember(memberRepository, request.getLoginId());
        member.compareToPassword(memberPasswordService, request.getPassword(), keyPair);
        // 토큰 생성 및 반환
    }
}
