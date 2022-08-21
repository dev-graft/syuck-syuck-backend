package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class MembershipServiceTest {
    MembershipRequestValidator validator = Mockito.mock(MembershipRequestValidator.class);
    MembershipService membershipService = new MembershipService(validator);

    @DisplayName("회원가입 요청이 조건과 맞지 않으면 에러")
    @Test
    void membership() {
        given(validator.validate(any()))
                .willReturn(List.of(ValidationError.of("field", "message")));

        ValidationException validationException = Assertions.catchThrowableOfType(
                () -> membershipService.membership(MembershipRequest.builder().build()),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotNull();
        assertThat(validationException.getErrors().get(0).getField()).isEqualTo("field");
        assertThat(validationException.getErrors().get(0).getMessage()).isEqualTo("message");
    }
}