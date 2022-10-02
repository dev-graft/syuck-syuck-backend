package devgraft.member.app;

import devgraft.support.exception.ValidationAsserts;
import devgraft.support.exception.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class DecryptedSignUpDataValidatorTest {
    private DecryptedSignUpDataValidator decryptedSignUpDataValidator;

    @BeforeEach
    void setUp() {
        decryptedSignUpDataValidator = new DecryptedSignUpDataValidator();
    }

    @DisplayName("회원가입 요청문의 필수 값이 null일 경우 에러")
    @Test
    void requiredElementsCheckHasError() {
        final DecryptedSignUpData givenRequest = new DecryptedSignUpData("", "", "", "");

        final List<ValidationError> errors = decryptedSignUpDataValidator.validate(givenRequest);

        ValidationAsserts.assertHasCall(errors, "loginId", "SignUpRequest.loginId must not be null.");
        ValidationAsserts.assertHasCall(errors, "password", "SignUpRequest.password must not be null.");
        ValidationAsserts.assertHasCall(errors, "nickname", "SignUpRequest.nickname must not be null.");
    }

    @DisplayName("")
    @Test
    void elementRegexMatchNotCorrectHasError() {
        final DecryptedSignUpData givenRequest = new DecryptedSignUpData("qwe", "qwe", "!", "");

        final List<ValidationError> errors = decryptedSignUpDataValidator.validate(givenRequest);

        ValidationAsserts.assertHasCall(errors, "loginId", "SignUpRequest.loginId pattern don't match.");
        ValidationAsserts.assertHasCall(errors, "password", "SignUpRequest.password pattern don't match.");
        ValidationAsserts.assertHasCall(errors, "nickname", "SignUpRequest.nickname pattern don't match.");
    }
}