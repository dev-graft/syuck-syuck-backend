package devgraft.module.member.app;

import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MemberFixture;
import devgraft.module.member.domain.MemberRepository;
import devgraft.module.member.domain.Password;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MembershipServiceTest {
    private MembershipService membershipService;
    private MembershipDecryptedDataProvider MembershipdecryptedDataProvider;
    private MemberCryptService memberCryptService;
    private MembershipDecryptedDataValidator membershipDecryptedDataValidator;
    private MemberRepository memberRepository;
    private MemberProvider memberProvider;

    @BeforeEach
    void setUp() {
        MembershipdecryptedDataProvider = mock(MembershipDecryptedDataProvider.class);
        memberCryptService = mock(MemberCryptService.class);
        membershipDecryptedDataValidator = mock(MembershipDecryptedDataValidator.class);
        memberRepository = mock(MemberRepository.class);
        memberProvider = mock(MemberProvider.class);

        given(MembershipdecryptedDataProvider.create(any(), any(), any())).willReturn(new MembershipDecryptedData("", "", "", ""));
        given(memberRepository.existsById(any())).willReturn(true);
        given(memberCryptService.hashingPassword(any())).willReturn(Password.from(""));
        given(memberProvider.create(any(), any())).willReturn(MemberFixture.anMember().build());

        membershipService = new MembershipService(MembershipdecryptedDataProvider, memberCryptService,
                membershipDecryptedDataValidator, memberRepository, memberProvider);
    }

    @DisplayName("회원가입 요청이 입력 조건과 맞지 않는 것이 있다면 에러")
    @Test
    void membershipRequestHasError() {
        final EncryptMembershipRequest givenRequest = new EncryptMembershipRequest("", "", "", "");
        given(membershipDecryptedDataValidator.validate(any())).willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException validationException = catchThrowableOfType(
                () -> membershipService.membership(givenRequest, null),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotNull();
        assertThat(validationException.getErrors()).isNotEmpty();
        assertThat(validationException.getErrors().get(0).getField()).isEqualTo("field");
        assertThat(validationException.getErrors().get(0).getMessage()).isEqualTo("message");
    }

    @DisplayName("회원가입 요청의 아이디가 이미 존재할 경우 에러")
    @Test
    void existsMemberByIdHasError() {
        final EncryptMembershipRequest givenRequest = new EncryptMembershipRequest("", "", "", "");
        given(memberRepository.existsById(any())).willReturn(true);

        assertThrows(AlreadyExistsMemberIdException.class, () ->
                membershipService.membership(givenRequest, null));

        verify(memberRepository, times(1)).existsById(any());
    }

    @DisplayName("회원가입 저장 호출")
    @Test
    void membershipWasCallOfMemberRepository_save() {
        final EncryptMembershipRequest givenRequest = new EncryptMembershipRequest("", "", "", "");
        given(memberRepository.existsById(any())).willReturn(false);

        membershipService.membership(givenRequest, null);

        verify(memberRepository, times(1)).save(any());
    }
}