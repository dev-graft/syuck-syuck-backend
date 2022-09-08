package devgraft.member.app;

import devgraft.member.domain.LoggedIn;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberPasswordService;
import devgraft.member.domain.MemberRepository;
import devgraft.member.domain.MemberStatus;
import devgraft.member.domain.NotCorrectPasswordException;
import devgraft.support.crypt.DecryptException;
import devgraft.support.crypt.RSA;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

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

    @Test
    void decryptPasswordHasError() {

    }

    @DisplayName("로그인 요청 입력 값 검증 에러")
    @Test
    void validateHasError() {
        given(validator.validate(any())).willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException validationException = catchThrowableOfType(
                () -> loginService.login(LoginRequest.builder().build(), RSA.generatedKeyPair()),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotEmpty();
    }

    @DisplayName("회원 찾기 실패 에러")
    @Test
    void notFindMemberError() {
        given(memberRepository.streamAllBy()).willReturn(Stream.empty());

        NoMemberException noMemberException = catchThrowableOfType(
                () -> loginService.login(LoginRequest.builder().build(), RSA.generatedKeyPair()),
                NoMemberException.class);

        assertThat(noMemberException).isNotNull();
    }

    @DisplayName("패스워드 검증 실패 에러")
    @Test
    void notCorrectPasswordHasError() {
        given(memberRepository.streamAllBy()).willReturn(
                Stream.of(Member.builder()
                        .loggedIn(LoggedIn.of("loggedId", "pwd"))
                        .status(MemberStatus.N).build()));
        given(memberPasswordService.decryptPassword(any(), any())).willThrow(DecryptException.class);

        NotCorrectPasswordException notCorrectPasswordException = catchThrowableOfType(
                () -> loginService.login(LoginRequest.builder().loginId("loggedId").build(), RSA.generatedKeyPair()),
                NotCorrectPasswordException.class);

        assertThat(notCorrectPasswordException).isNotNull();
    }
}