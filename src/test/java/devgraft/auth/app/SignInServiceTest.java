package devgraft.auth.app;

import devgraft.auth.domain.AuthMemberService;
import devgraft.auth.domain.AuthSession;
import devgraft.auth.domain.AuthSessionFixture;
import devgraft.auth.domain.AuthSessionRepository;
import devgraft.auth.domain.DecryptedSignInDataFixture;
import devgraft.support.crypto.KeyPairFixture;
import devgraft.support.jwt.JwtIssueRequest;
import devgraft.support.jwt.JwtIssuedResult;
import devgraft.support.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SignInServiceTest {
    private SignInService signInService;
    private SignInRequestDecoder mockSignInRequestDecoder;
    private AuthMemberService mockAuthMemberService;
    private AuthSessionProvider mockAuthSessionProvider;
    private JwtProvider mockJwtProvider;
    private AuthSessionRepository mockAuthSessionRepository;
    @BeforeEach
    void setUp() {
        mockSignInRequestDecoder = mock(SignInRequestDecoder.class);
        mockAuthMemberService = mock(AuthMemberService.class);
        mockAuthSessionProvider = mock(AuthSessionProvider.class);
        mockJwtProvider = mock(JwtProvider.class);
        mockAuthSessionRepository = mock(AuthSessionRepository.class);

        given(mockSignInRequestDecoder.decrypt(any(), any())).willReturn(DecryptedSignInDataFixture.anData().build());
        given(mockAuthMemberService.authenticate(any())).willReturn(new AuthMemberService.AuthenticateMemberResult("success", true));
        given(mockAuthSessionProvider.create(any())).willReturn(AuthSessionFixture.anAuthSession().build());
        given(mockJwtProvider.issue(any())).willReturn(JwtIssuedResult.of("aT", "rT"));

        signInService = new SignInService(mockSignInRequestDecoder, mockAuthMemberService, mockAuthSessionProvider, mockJwtProvider, mockAuthSessionRepository);
    }

    @DisplayName("로그인 요청문(암호) 정보 복호화 요청")
    @Test
    void signIn_passes_Request_To_SignInRequestDecoder() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockSignInRequestDecoder).decrypt(refEq(givenRequest), eq(givenKeyPair));
    }


    @DisplayName("로그인 요청문 기반 회원 서비스에 정보 인증 요청")
    @Test
    void signIn_passes_Request_To_AuthMemberService() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockAuthMemberService).authenticate(isA(AuthMemberService.AuthenticateMemberRequest.class));
    }

    @DisplayName("로그인 요청에서 회원 인증 실패 예외처리")
    @Test
    void signIn_throw_SignInAuthenticationFailedException() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockAuthMemberService.authenticate(any())).willReturn(new AuthMemberService.AuthenticateMemberResult("failed", false));

        assertThrows(SignInAuthenticationFailedException.class,
                () -> signInService.signIn(givenRequest, givenKeyPair));
    }

    @DisplayName("로그인 요청정보를 기반으로 AuthSession 인스턴스 생성 요청")
    @Test
    void signIn_passes_SignInData_To_AuthSessionProvider() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        final SignInRequestDecoder.DecryptedSignInData givenSignData = DecryptedSignInDataFixture.anData().loginId("devgraft").build();
        given(mockSignInRequestDecoder.decrypt(any(), any())).willReturn(givenSignData);

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockAuthSessionProvider, times(1)).create(refEq(givenSignData));
    }

    @DisplayName("AuthSession의 유니크 아이디를 기반으로 Jwt 인스턴스 생성 요청")
    @Test
    void signIn_passes_Request_To_JwtProvider() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        final AuthSession givenAuthSession = AuthSessionFixture.anAuthSession().uniqId("devgraft").build();
        given(mockAuthSessionProvider.create(any())).willReturn(givenAuthSession);

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockJwtProvider, times(1)).issue(refEq(JwtIssueRequest.of("devgraft")));
    }

    @DisplayName("AuthSession 리포지토리 저장")
    @Test
    void signIn_passes_AuthSession_To_Repository() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        final AuthSession givenAuthSession = AuthSessionFixture.anAuthSession().uniqId("devgraft").build();
        given(mockAuthSessionProvider.create(any())).willReturn(givenAuthSession);

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockAuthSessionRepository, times(1)).save(refEq(givenAuthSession));
    }

    @DisplayName("로그인 결과")
    @Test
    void signIn_returnValue() {
        final SignInService.EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        final JwtIssuedResult givenJwtIssuedResult = JwtIssuedResult.of("givenA", "givenR");
        given(mockJwtProvider.issue(any())).willReturn(givenJwtIssuedResult);

        final SignInService.SignInResult signInResult = signInService.signIn(givenRequest, givenKeyPair);

        assertThat(signInResult.getAccessToken()).isEqualTo(givenJwtIssuedResult.getAccessToken());
        assertThat(signInResult.getRefreshToken()).isEqualTo(givenJwtIssuedResult.getRefreshToken());
    }
}