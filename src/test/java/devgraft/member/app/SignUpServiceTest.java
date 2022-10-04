package devgraft.member.app;

import devgraft.member.domain.AlreadyExistsMemberIdException;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberFixture;
import devgraft.member.domain.MemberId;
import devgraft.member.domain.MemberRepository;
import devgraft.member.domain.Password;
import devgraft.support.crypto.KeyPairFixture;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SignUpServiceTest {
    private SignUpService signUpService;
    private SignUpRequestDecoder mockSignUpRequestDecoder;
    private DecryptedSignUpDataValidator mockDecryptedSignUpDataValidator;
    private MemberRepository mockMemberRepository;
    private MemberProvider mockMemberProvider;

    @BeforeEach
    void setUp() {
        mockSignUpRequestDecoder = mock(SignUpRequestDecoder.class);
        mockDecryptedSignUpDataValidator = mock(DecryptedSignUpDataValidator.class);
        mockMemberRepository = mock(MemberRepository.class);
        mockMemberProvider = mock(MemberProvider.class);

        given(mockSignUpRequestDecoder.decrypt(any(), any()))
                .willReturn(new DecryptedSignUpData("loginId", "password", "nickname", "profileImage"));
        given(mockMemberRepository.existsById(any())).willReturn(false);
        given(mockMemberProvider.create(any()))
                .willReturn(MemberFixture.anMember().build());

        signUpService = new SignUpService(mockSignUpRequestDecoder, mockDecryptedSignUpDataValidator, mockMemberRepository, mockMemberProvider);
    }

    @DisplayName("회원가입 요청문(암호) 복호화 요청하는지 검사")
    @Test
    void signUp_passes_EncryptMembershipRequest_To_DecryptedSignUpDataProvider() {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        signUpService.signUp(givenRequest, givenKeyPair);

        verify(mockSignUpRequestDecoder, times(1)).decrypt(refEq(givenRequest), eq(givenKeyPair));
    }

    @DisplayName("회원가입 요청문(복호화) 정규식에 알맞지 않은 내용 예외처리")
    @Test
    void signUp_DecryptedSignUpData_Validate_HasError() {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();

        given(mockDecryptedSignUpDataValidator.validate(any()))
                .willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException errors = catchThrowableOfType(() ->
                        signUpService.signUp(givenRequest, givenKeyPair),
                ValidationException.class);

        assertThat(errors).isNotNull();
        assertThat(errors.getErrors()).isNotEmpty();
    }

    @DisplayName("회원가입 아이디 중복 예외처리")
    @Test
    void signUp_throw_AlreadyExistsMemberIdException() {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        given(mockMemberRepository.existsById(any())).willReturn(true);

        assertThrows(AlreadyExistsMemberIdException.class,
                () -> signUpService.signUp(givenRequest, givenKeyPair));

        verify(mockMemberRepository, times(1)).existsById(isA(MemberId.class));
    }

    @DisplayName("회원가입 Repository 저장하는지 검사")
    @Test
    void signUp_passesMemberToRepository() {
        final EncryptedSignUpRequest givenRequest = EncryptedSignUpRequestFixture.anRequest().build();
        final KeyPair givenKeyPair = KeyPairFixture.anKeyPair();
        final Member givenMember = MemberFixture.anMember()
                .id(MemberId.from("qwe123"))
                .password(Password.from("PWPWPW"))
                .build();
        given(mockMemberProvider.create(any())).willReturn(givenMember);

        signUpService.signUp(givenRequest, givenKeyPair);

        verify(mockMemberRepository, times(1)).save(refEq(givenMember));
    }
}