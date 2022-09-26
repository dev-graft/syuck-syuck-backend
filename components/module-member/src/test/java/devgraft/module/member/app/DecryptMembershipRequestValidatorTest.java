package devgraft.module.member.app;

import devgraft.support.exception.ValidationAsserts;
import devgraft.support.exception.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DecryptMembershipRequestValidatorTest {
    private DecryptMembershipRequestValidator decryptMembershipRequestValidator;

    @BeforeEach
    void setUp() {
        decryptMembershipRequestValidator = new DecryptMembershipRequestValidator();
    }

    @DisplayName("회원가입 요청문의 필수 값이 null일 경우 에러")
    @Test
    void requiredElementsCheckHasError() {
        final DecryptMembershipRequest givenRequest = new DecryptMembershipRequest("", "", "", "");

        final List<ValidationError> errors = decryptMembershipRequestValidator.validate(givenRequest);

        ValidationAsserts.assertHasCall(errors, "loginId", "MembershipRequest.loginId must not be null.");
        ValidationAsserts.assertHasCall(errors, "password", "MembershipRequest.password must not be null.");
        ValidationAsserts.assertHasCall(errors, "nickname", "MembershipRequest.nickname must not be null.");
    }

    @DisplayName("")
    @Test
    void elementRegexMatchNotCorrectHasError() {
        final DecryptMembershipRequest givenRequest = new DecryptMembershipRequest("qwe", "qwe", "!", "");

        final List<ValidationError> errors = decryptMembershipRequestValidator.validate(givenRequest);

        ValidationAsserts.assertHasCall(errors, "loginId", "MembershipRequest.loginId pattern don't match.");
        ValidationAsserts.assertHasCall(errors, "password", "MembershipRequest.password pattern don't match.");
        ValidationAsserts.assertHasCall(errors, "nickname", "MembershipRequest.nickname pattern don't match.");
    }
}