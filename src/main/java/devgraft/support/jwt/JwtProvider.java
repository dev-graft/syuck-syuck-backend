package devgraft.support.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtProperties jwtProperties; // access 만료, refresh 만료

    // JWT 발급
    public JwtIssuedResult issue(final JwtIssueRequest request) {
        return null;
    }

    // JWT 검증 (검증된 정보 반환 / 언제 생성되었고, 어떤 값이 저장되어 있는지)
    public JwtVerifyResult verify() throws JwtExpiredException, JwtValidationFailedException {
        // accessToken 만료 검증
//        throw new JwtExpiredException();
        // access & refresh 일치하는지 검증
//        throw new JwtValidationFailedException();
        return null;
    }
}
