package devgraft.member.app;

import devgraft.member.domain.MemberCryptoService;
import devgraft.support.crypto.KeyPairFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SignUpCodeServiceTest {
    private SignUpCodeService signUpCodeService;
    private MemberCryptoService mockMemberCryptoService;

    @BeforeEach
    void setUp() {
        mockMemberCryptoService = mock(MemberCryptoService.class);

        signUpCodeService = new SignUpCodeService(mockMemberCryptoService);
    }

    @DisplayName("회원가입 공개키 생성 결과")
    @Test
    void generatedSignUpCode_returnValue() {
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockMemberCryptoService.generatedCryptoKey()).willReturn(givenKeyPair);

        final KeyPair keyPair = signUpCodeService.generatedSignUpCode();

        assertThat(keyPair).isEqualTo(givenKeyPair);
    }
}