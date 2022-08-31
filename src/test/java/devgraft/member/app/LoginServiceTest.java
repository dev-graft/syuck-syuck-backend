package devgraft.member.app;

import devgraft.support.crypt.RSA;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class LoginServiceTest {
    private MemberPasswordHelper passwordHelper;
    private LoginRequestValidator validator;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        passwordHelper = Mockito.mock(MemberPasswordHelper.class);
        validator = Mockito.mock(LoginRequestValidator.class);
        loginService = new LoginService(passwordHelper, validator);
    }

    @DisplayName("로그인 요청 입력 값 검증 에러")
    @Test
    void validateHasError() {
        given(validator.validate(any())).willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException validationException = Assertions.catchThrowableOfType(
                () -> loginService.login(LoginRequest.builder().build(), RSA.generatedKeyPair()),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotEmpty();
    }

    @Test
    void decryptPasswordHasError() {

    }
}