package devgraft.auth.app;

import devgraft.auth.domain.AuthCryptoService;
import devgraft.support.crypto.KeyPairFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SignInCodeServiceTest {
    private SignInCodeService signInCodeService;
    private AuthCryptoService mockAuthCryptoService;
    @BeforeEach
    void setUp() {
        mockAuthCryptoService = mock(AuthCryptoService.class);

        signInCodeService = new SignInCodeService(mockAuthCryptoService);
    }

    @DisplayName("로그인용 공개키 도메인 로직 호출 검사")
    @Test
    void generatedSignUpCode_wasCall_generatedCryptoKey_To_AuthCryptoService() {
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockAuthCryptoService.generatedCryptoKey()).willReturn(givenKeyPair);

        signInCodeService.generateSignInCode();

        verify(mockAuthCryptoService, times(1)).generatedCryptoKey();
    }

    @DisplayName("로그인용 공개키 생성 결과")
    @Test
    void generatedSignUpCode_returnValue() {
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockAuthCryptoService.generatedCryptoKey()).willReturn(givenKeyPair);

        final KeyPair result = signInCodeService.generateSignInCode();

        assertThat(result).isEqualTo(givenKeyPair);
    }
}