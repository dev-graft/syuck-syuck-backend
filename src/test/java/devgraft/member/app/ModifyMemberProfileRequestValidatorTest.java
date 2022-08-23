package devgraft.member.app;

import devgraft.support.exception.ValidationAsserts;
import devgraft.support.exception.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ModifyMemberProfileRequestValidatorTest {
    private ModifyMemberProfileRequestValidator validator = new ModifyMemberProfileRequestValidator();

    @DisplayName("회원 프로필 수정 입력 값이 정규식에 일치하지 않으면 에러")
    @Test
    void validatedHasError() {
        List<ValidationError> errors = validator.validate(ModifyMemberProfileRequest.builder()
                .nickname("!@#")
                .build());

        ValidationAsserts.assertHasCall(errors, "nickname", "ModifyMemberProfileRequest.nickname pattern don't match.");
    }
}