package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberPasswordService;
import devgraft.member.domain.SpyMemberRepository;
import devgraft.support.crypt.RSA;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MembershipServiceTest {
    private SpyMemberRepository spyMemberRepository;
    private MembershipRequestValidator validator;
    private MembershipService membershipService;
    private MemberPasswordService mockMemberPasswordService;

    @BeforeEach
    void setUp() {
        spyMemberRepository = spy(SpyMemberRepository.class);
        validator = mock(MembershipRequestValidator.class);
        mockMemberPasswordService = mock(MemberPasswordService.class);
        membershipService = new MembershipService(spyMemberRepository, validator, mockMemberPasswordService);
    }

    @DisplayName("복호화 키가 일치하지 않으면 에러")
    @Test
    void notMatchKey() {
        given(mockMemberPasswordService.decryptPassword(any(), any())).willThrow(new MembershipDecryptFailedException());

        final MembershipDecryptFailedException membershipDecryptFailedException = Assertions.catchThrowableOfType(
                () -> membershipService.membership(MembershipRequest.builder().build(), RSA.generatedKeyPair()),
                MembershipDecryptFailedException.class);

        assertThat(membershipDecryptFailedException).isNotNull();
    }

    @DisplayName("회원가입 요청이 입력 조건과 맞지 않으면 에러")
    @Test
    void membershipHasError() {
        given(validator.validate(any())).willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException validationException = Assertions.catchThrowableOfType(
                () -> membershipService.membership(MembershipRequest.builder().build(), RSA.generatedKeyPair()),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotNull();
        assertThat(validationException.getErrors().get(0).getField()).isEqualTo("field");
        assertThat(validationException.getErrors().get(0).getMessage()).isEqualTo("message");
    }

    @DisplayName("회원가입 요청의 아이디가 중복일 경우 에러")
    @Test
    void existsMemberByIdHasError() {
        given(spyMemberRepository.existsByNickname(any())).willReturn(true);

        final AlreadyExistsLoginIdException alreadyExistsLoginIdException = Assertions.catchThrowableOfType(
                () -> membershipService.membership(MembershipRequest.builder().build(), RSA.generatedKeyPair()),
                AlreadyExistsLoginIdException.class);

        assertThat(alreadyExistsLoginIdException).isNotNull();
    }

    @DisplayName("회원 Repo 저장 요청 및 정상 결과")
    @Test
    void membershipSuccess() {
        String givenLoginId = "loginId";
        final MembershipRequest givenRequest = MembershipRequest.builder()
                .loginId(givenLoginId)
                .password("password")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();
        String givenHashPassword = "hash_password";
        given(mockMemberPasswordService.hashingPassword(any())).willReturn(givenHashPassword);

        MemberIds memberIds = membershipService.membership(givenRequest, RSA.generatedKeyPair());

        verify(spyMemberRepository, times(1)).save(any(Member.class));

//        assertThat(spyMemberRepository.data.get(1L).getLoginId()).isEqualTo(givenLoginId);
//        assertThat(spyMemberRepository.data.get(1L).getPassword()).isEqualTo(givenHashPassword);
        assertThat(spyMemberRepository.data.get(1L).getNickname()).isEqualTo("nickname");
        assertThat(spyMemberRepository.data.get(1L).getProfileImage()).isEqualTo("profileImage");
        assertThat(memberIds.getId()).isEqualTo(1L);
        assertThat(memberIds.getLoginId()).isEqualTo(givenLoginId);
    }
}