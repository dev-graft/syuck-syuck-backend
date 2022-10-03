package devgraft.auth.app;

import devgraft.auth.domain.AuthMemberService;
import devgraft.auth.domain.AuthenticateMemberRequest;
import devgraft.auth.domain.AuthenticateMemberResult;
import devgraft.auth.domain.DecryptedSignInData;
import devgraft.support.crypto.KeyPairFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SignInServiceTest {
    private SignInService signInService;
    private SignInRequestDecoder mockSignInRequestDecoder;
    private AuthMemberService mockAuthMemberService;

    @BeforeEach
    void setUp() {
        mockSignInRequestDecoder = mock(SignInRequestDecoder.class);
        mockAuthMemberService = mock(AuthMemberService.class);
        given(mockSignInRequestDecoder.decrypt(any(), any())).willReturn(DecryptedSignInData.builder().build());
        given(mockAuthMemberService.authenticate(any())).willReturn(new AuthenticateMemberResult("success", true));

        signInService = new SignInService(mockSignInRequestDecoder, mockAuthMemberService);
    }

    @DisplayName("로그인 요청문(암호) 정보 복호화 요청")
    @Test
    void signIn_passes_Request_To_SignInRequestDecoder() {
        final EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockSignInRequestDecoder).decrypt(refEq(givenRequest), eq(givenKeyPair));
    }


    @DisplayName("로그인 요청문 기반 회원 서비스에 정보 인증 요청")
    @Test
    void signIn_passes_Request_To_AuthMemberService() {
        final EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        signInService.signIn(givenRequest, givenKeyPair);

        verify(mockAuthMemberService).authenticate(isA(AuthenticateMemberRequest.class));
    }

    @DisplayName("로그인 요청에서 회원 인증 실패 예외처리")
    @Test
    void signIn_throw_SignInAuthenticationFailedException() {
        final EncryptedSignInRequest givenRequest = EncryptedSignInRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockAuthMemberService.authenticate(any())).willReturn(new AuthenticateMemberResult("failed", false));

        assertThrows(SignInAuthenticationFailedException.class,
                () -> signInService.signIn(givenRequest, givenKeyPair));
    }
}