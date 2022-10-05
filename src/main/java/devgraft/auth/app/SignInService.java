package devgraft.auth.app;


import devgraft.auth.domain.AuthMemberService;
import devgraft.auth.domain.AuthSession;
import devgraft.auth.domain.AuthSessionRepository;
import devgraft.common.JsonLogger;
import devgraft.support.jwt.JwtIssueRequest;
import devgraft.support.jwt.JwtIssuedResult;
import devgraft.support.jwt.JwtProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignInService {
    private final SignInRequestDecoder signInRequestDecoder;
    private final AuthMemberService authMemberService;
    private final AuthSessionProvider authSessionProvider;
    private final JwtProvider jwtProvider;
    private final AuthSessionRepository authSessionRepository;

    public SignInResult signIn(final EncryptedSignInRequest request, final KeyPair keyPair) {
        final SignInRequestDecoder.DecryptedSignInData signInData = signInRequestDecoder.decrypt(request, keyPair);
        final AuthMemberService.AuthenticateMemberResult authResult = authMemberService.authenticate(signInData.toRequest());
        if (!authResult.isSuccess()) {
            JsonLogger.logI(log, "SignInService.signIn authResult is Failed message: {}", authResult.getMessage());
            throw new SignInAuthenticationFailedException();
        }

        final AuthSession authSession = authSessionProvider.create(signInData);

        final JwtIssuedResult jwt = jwtProvider.issue(JwtIssueRequest.of(authSession.getUniqId()));

        authSessionRepository.save(authSession);

        return SignInResult.of(jwt.getAccessToken(), jwt.getRefreshToken());
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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SignInResult {
        private final String accessToken;

        private final String refreshToken;
        public static SignInResult of(final String accessToken, final String refreshToken) {
            return new SignInResult(accessToken, refreshToken);
        }

    }
}
