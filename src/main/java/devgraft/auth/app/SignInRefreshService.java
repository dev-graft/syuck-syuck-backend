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
        final AuthCodeVerifyResult verifyResult = authCodeService.verify(request);
        final AuthSession authSession = authSessionRepository.findById(verifyResult.getUniqId()).orElseThrow(NotFoundAuthSessionException::new);
        if (authSession.isBlock()) throw new BlockAuthSessionException();
        final AuthCodeGeneratedResult newAuthCode = authCodeService.generate(verifyResult.getUniqId());
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
