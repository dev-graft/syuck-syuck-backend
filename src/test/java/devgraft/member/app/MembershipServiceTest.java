package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.SpyMemberRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MembershipServiceTest {
    SpyMemberRepository spyMemberRepository;
    MembershipRequestValidator validator;
    MembershipService membershipService;

    @BeforeEach
    void setUp() {
        spyMemberRepository = Mockito.spy(SpyMemberRepository.class);
        validator = Mockito.mock(MembershipRequestValidator.class);
        membershipService = new MembershipService(spyMemberRepository, validator);
    }

    @DisplayName("회원가입 요청이 조건과 맞지 않으면 에러")
    @Test
    void membershipHasError() {
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

    @DisplayName("회원가입 성공")
    @Test
    void membershipSuccess() {
        MembershipRequest givenRequest = MembershipRequest.builder()
                .memberId("memberId")
                .password("password")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();
        given(validator.validate(givenRequest)).willReturn(List.of());

        MemberId result = membershipService.membership(givenRequest);

        verify(validator, times(1)).validate(eq(givenRequest));
        verify(spyMemberRepository, times(1)).save(any(Member.class));

        assertThat(spyMemberRepository.data.get(1L).getId()).isEqualTo("memberId");
        assertThat(spyMemberRepository.data.get(1L).getPassword()).isEqualTo("password");
        assertThat(spyMemberRepository.data.get(1L).getNickname()).isEqualTo("nickname");
        assertThat(spyMemberRepository.data.get(1L).getProfileImage()).isEqualTo("profileImage");
//        assertThat(result.getIdx()).isEqualTo(1L);
        assertThat(result.getId()).isEqualTo("memberId");
    }
}