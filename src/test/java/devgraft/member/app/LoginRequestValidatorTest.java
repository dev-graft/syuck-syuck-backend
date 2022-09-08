package devgraft.member.app;

import devgraft.support.exception.ValidationAsserts;
import devgraft.support.exception.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class LoginRequestValidatorTest {
    private static final LoginRequestValidator validator = new LoginRequestValidator();

    @DisplayName("로그인 요청의 필수 값에 null이 있을 경우 에러가 발생한다.")
    @Test
    void requiredElementsCheckHasError() {
        final List<ValidationError> errors = validator.validate(LoginRequest.builder().build());

        ValidationAsserts.assertHasCall(errors, "loginId", "LoginRequest.loginId must not be null.");
        ValidationAsserts.assertHasCall(errors, "password", "LoginRequest.password must not be null.");
    }
}