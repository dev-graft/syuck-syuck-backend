package devgraft.member.app;

import devgraft.support.exception.ValidationAsserts;
import devgraft.support.exception.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class MembershipRequestValidatorTest {
    MembershipRequestValidator validator = new MembershipRequestValidator();

    @DisplayName("회원가입의 필수 입력 값은 비어있을 수 없다.")
    @Test
    void requiredElementsCheck() {
        MembershipRequest request = MembershipRequest.builder()
                .build();

        List<ValidationError> errors = validator.validate(request);

        ValidationAsserts.assertHasCall(errors, "memberId", "MembershipRequest.memberId must not be null.");
        ValidationAsserts.assertHasCall(errors, "password", "MembershipRequest.password must not be null.");
        ValidationAsserts.assertHasCall(errors, "nickname", "MembershipRequest.nickname must not be null.");
    }
}