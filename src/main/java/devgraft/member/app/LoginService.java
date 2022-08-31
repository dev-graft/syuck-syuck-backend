package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberPasswordHelper memberPasswordHelper;
    private final LoginRequestValidator loginRequestValidator;
    public void login(LoginRequest loginRequest, KeyPair keyPair) {
        List<ValidationError> errors = loginRequestValidator.validate(loginRequest);
        if (!errors.isEmpty()) throw new ValidationException(errors, "로그인 요청이 실패하였습니다.");

    }
    // 전달받은 패스워드 복호화 - 복호화 에러
    // 아이디 기준 사용자 조회 - 존재하지 않음 에러
    // 패스워드 비교 - 틀림 에러
    // 토큰 생성 및 반환
}
