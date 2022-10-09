package devgraft.auth.app;

import devgraft.auth.domain.AuthSessionRepository;
import devgraft.auth.domain.AuthorizationElements;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignInRefreshService {
    private final AuthSessionRepository authSessionRepository;

    public SignInRefreshResult refresh(final SignInRefreshRequest request) {
        // refresh 검증(키쌍, 만료)
        // 결과에 나온 uniqId를 기반으로 AuthSession 조회
        // Block 상태인지 검사
        // uniqId를 기반으로 새로운 authorizationElements 정보 생성
        // authorizationElements 반환
        return null;
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
