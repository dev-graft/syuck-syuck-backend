package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.SpyMemberRepository;
import devgraft.member.exception.AlreadyExistsLoginIdException;
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
    private SpyMemberRepository spyMemberRepository;
    private MembershipRequestValidator validator;
    private MembershipService membershipService;

    @BeforeEach
    void setUp() {
        spyMemberRepository = Mockito.spy(SpyMemberRepository.class);
        validator = Mockito.mock(MembershipRequestValidator.class);
        membershipService = new MembershipService(spyMemberRepository, validator);
    }

    @DisplayName("회원가입 요청이 조건과 맞지 않으면 에러")
    @Test
    void membershipHasError() {
        final MembershipRequest givenRequest = MembershipRequest.builder()
                .loginId("memberId")
                .password("password")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();
        given(validator.validate(givenRequest))
                .willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException validationException = Assertions.catchThrowableOfType(
                () -> membershipService.membership(givenRequest),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotNull();
        assertThat(validationException.getErrors().get(0).getField()).isEqualTo("field");
        assertThat(validationException.getErrors().get(0).getMessage()).isEqualTo("message");
    }

    @DisplayName("회원가입 요청의 아이디가 중복일 경우 에러")
    @Test
    void existsMemberByIdHasError() {
        final MembershipRequest givenRequest = MembershipRequest.builder()
                .loginId("memberId")
                .password("password")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();
        given(validator.validate(givenRequest)).willReturn(List.of());
        given(spyMemberRepository.existsByLoginId(eq("memberId"))).willReturn(true);

        final AlreadyExistsLoginIdException alreadyExistsLoginIdException = Assertions.catchThrowableOfType(
                () -> membershipService.membership(givenRequest),
                AlreadyExistsLoginIdException.class);

        assertThat(alreadyExistsLoginIdException).isNotNull();
    }

    @DisplayName("회원가입 성공")
    @Test
    void membershipSuccess() {
        final MembershipRequest givenRequest = MembershipRequest.builder()
                .loginId("memberId")
                .password("password")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();
        given(validator.validate(givenRequest)).willReturn(List.of());

        final MemberIds result = membershipService.membership(givenRequest);

        verify(validator, times(1)).validate(eq(givenRequest));
        verify(spyMemberRepository, times(1)).save(any(Member.class));

        assertThat(spyMemberRepository.data.get(1L).getLoginId()).isEqualTo("memberId");
        assertThat(spyMemberRepository.data.get(1L).getPassword()).isEqualTo("password");
        assertThat(spyMemberRepository.data.get(1L).getNickname()).isEqualTo("nickname");
        assertThat(spyMemberRepository.data.get(1L).getProfileImage()).isEqualTo("profileImage");
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLoginId()).isEqualTo("memberId");
    }
}