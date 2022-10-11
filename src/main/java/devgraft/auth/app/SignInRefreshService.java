package devgraft.auth.app;

import devgraft.auth.domain.AuthCodeService;
import devgraft.auth.domain.AuthCodeService.AuthCodeGeneratedResult;
import devgraft.auth.domain.AuthCodeService.AuthCodeVerifyResult;
import devgraft.auth.domain.AuthSession;
import devgraft.auth.domain.AuthSessionRepository;
import devgraft.auth.domain.AuthorizationElements;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignInRefreshService {
    private final AuthCodeService authCodeService;
    private final AuthSessionRepository authSessionRepository;

    public SignInRefreshResult refresh(final SignInRefreshRequest request) {
        // refresh 검증(키쌍, 만료)
        final AuthCodeVerifyResult verifyResult = authCodeService.verify(request);
        final String uniqId = verifyResult.orElseThrow(AuthCodeVerifyException::new);
        // 결과에 나온 uniqId를 기반으로 AuthSession 조회
        final AuthSession authSession = authSessionRepository.findById(uniqId).orElseThrow();
        // Block 상태인지 검사
        if (authSession.isBlock()) throw new RuntimeException();
        final AuthCodeGeneratedResult newAuthCode = authCodeService.generate(null);
        return new SignInRefreshResult(newAuthCode.getAccessToken(), newAuthCode.getRefreshToken());
    }

    @AllArgsConstructor
    @Getter
    public static class SignInRefreshRequest implements AuthorizationElements {
        private final String accessToken;
        private final String refreshToken;
    }

    @AllArgsConstructor
    @Getter
    public static class SignInRefreshResult implements AuthorizationElements {
        private final String accessToken;
        private final String refreshToken;
    }
}
