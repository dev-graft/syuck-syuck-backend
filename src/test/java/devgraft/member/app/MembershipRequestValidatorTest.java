package devgraft.member.app;

import devgraft.support.exception.ValidationAsserts;
import devgraft.support.exception.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class MembershipRequestValidatorTest {
    MembershipRequestValidator validator = new MembershipRequestValidator();

    @DisplayName("회원가입의 필수 값이 입력되지 않으면 에러가 발생한다")
    @Test
    void requiredElementsCheck() {
        MembershipRequest request = MembershipRequest.builder()
                .build();

        List<ValidationError> errors = validator.validate(request);

        ValidationAsserts.assertHasCall(errors, "memberId", "MembershipRequest.memberId must not be null.");
        ValidationAsserts.assertHasCall(errors, "password", "MembershipRequest.password must not be null.");
        ValidationAsserts.assertHasCall(errors, "nickname", "MembershipRequest.nickname must not be null.");

    }

    @DisplayName("회원가입의 입력 값이 정규식에 일치하지 않으면 에러가 발생한다")
    @Test
    void validatePatternTest() {
        MembershipRequest request = MembershipRequest.builder()
                .memberId("a")
                .password("a")
                .nickname("a@")
                .build();

        List<ValidationError> errors = validator.validate(request);

        ValidationAsserts.assertHasCall(errors, "memberId", "MembershipRequest.memberId pattern must match.");
        ValidationAsserts.assertHasCall(errors, "password", "MembershipRequest.password pattern must match.");
        ValidationAsserts.assertHasCall(errors, "nickname", "MembershipRequest.nickname pattern must match.");
    }
}