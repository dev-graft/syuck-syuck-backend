package devgraft.member.app;

import devgraft.member.domain.LoggedIn;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberPasswordService;
import devgraft.member.domain.MemberRepository;
import devgraft.member.domain.MemberStatus;
import devgraft.member.domain.NotCorrectPasswordException;
import devgraft.support.crypto.DecryptException;
import devgraft.support.crypto.RSA;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.KeyPair;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class LoginServiceTest {
    private MemberPasswordService memberPasswordService;
    private LoginRequestValidator validator;
    private MemberRepository memberRepository;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        memberPasswordService = Mockito.mock(MemberPasswordService.class);
        validator = Mockito.mock(LoginRequestValidator.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        loginService = new LoginService(memberPasswordService, validator, memberRepository);
    }

    @DisplayName("로그인 요청 입력 값 검증 에러")
    @Test
    void validateHasError() {
        given(validator.validate(any())).willReturn(List.of(ValidationError.of("field", "message")));
        final LoginRequest givenRequest = LoginRequest.builder().build();

        final ValidationException validationException = catchThrowableOfType(
                () -> loginService.login(givenRequest, null),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotEmpty();
    }

    @DisplayName("회원 찾기 실패 에러")
    @Test
    void notFindMemberError() {
        given(memberRepository.findByLoggedId(any())).willReturn(Optional.empty());
        final LoginRequest givenRequest = LoginRequest.builder().build();

        NoMemberException noMemberException = catchThrowableOfType(
                () -> loginService.login(givenRequest, null),
                NoMemberException.class);

        assertThat(noMemberException).isNotNull();
    }

    @DisplayName("패스워드 검증 실패 에러")
    @Test
    void notCorrectPasswordHasError() {
        given(memberRepository.findByLoggedId(any())).willReturn(Optional.of(Member.builder()
                .loggedIn(LoggedIn.of("loggedId", "pwd"))
                .status(MemberStatus.N).build()));
        given(memberPasswordService.decryptPassword(any(), any())).willThrow(DecryptException.class);
        final LoginRequest givenRequest = LoginRequest.builder().loginId("loggedId").build();
        final KeyPair givenKeyPair = RSA.generatedKeyPair();

        NotCorrectPasswordException notCorrectPasswordException = catchThrowableOfType(
                () -> loginService.login(givenRequest, givenKeyPair),
                NotCorrectPasswordException.class);

        assertThat(notCorrectPasswordException).isNotNull();
    }
}