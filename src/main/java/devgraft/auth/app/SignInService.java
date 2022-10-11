package devgraft.auth.app;

import devgraft.auth.app.SignInRequestDecoder.DecryptedSignInData;
import devgraft.auth.domain.AuthCodeService;
import devgraft.auth.domain.AuthCodeService.AuthCodeGeneratedResult;
import devgraft.auth.domain.AuthSession;
import devgraft.auth.domain.AuthSessionRepository;
import devgraft.auth.domain.AuthorizationElements;
import devgraft.auth.domain.MemberAuthenticationService;
import devgraft.auth.domain.MemberAuthenticationService.AuthenticateMemberRequest;
import devgraft.auth.domain.MemberAuthenticationService.AuthenticateMemberResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@RequiredArgsConstructor
@Service
public class SignInService {
    private final SignInRequestDecoder signInRequestDecoder;
    private final MemberAuthenticationService memberAuthenticationService;
    private final AuthSessionProvider authSessionProvider;
    private final AuthSessionRepository authSessionRepository;
    private final AuthCodeService authCodeService;

    public SignInResult signIn(final EncryptedSignInRequest request, final KeyPair keyPair) {
        final DecryptedSignInData decrypt = signInRequestDecoder.decrypt(request, keyPair);
        final AuthenticateMemberResult authenticateResult = memberAuthenticationService.authenticate(new AuthenticateMemberRequest(decrypt.getLoginId(), decrypt.getPassword()));
        if (!authenticateResult.isSuccess()) throw new SignInAuthenticationFailedException();
        final AuthSession authSession = authSessionProvider.create(decrypt);
        authSessionRepository.save(authSession);
        final AuthCodeGeneratedResult generate = authCodeService.generate(authSession.getUniqId());
        return new SignInResult(generate.getAccessToken(), generate.getRefreshToken());
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class EncryptedSignInRequest {
        private String loginId;
        private String password;
        private String pushToken;
        private String os;
        private String deviceName;
    }

    @AllArgsConstructor
    @Getter
    public static class SignInResult implements AuthorizationElements {
        private String accessToken;
        private String refreshToken;
    }
}
