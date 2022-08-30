package devgraft.member.app;

import devgraft.member.domain.LoggedIn;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberStatus;
import devgraft.member.domain.SpyMemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ModifyMemberProfileServiceTest {
    private SpyMemberRepository spyMemberRepository;
    private ModifyMemberProfileService modifyMemberProfileService;
    private ModifyMemberProfileRequestValidator mockValidator;

    @BeforeEach
    void setUp() {
        spyMemberRepository = Mockito.spy(SpyMemberRepository.class);
        mockValidator = Mockito.mock(ModifyMemberProfileRequestValidator.class);
        modifyMemberProfileService = new ModifyMemberProfileService(spyMemberRepository, mockValidator);
    }

    @DisplayName("수정 대상 회원이 없을 경우 에러(삭제 상태도 판별)")
    @Test
    void notExistsHasError() {
        given(spyMemberRepository.findById(any())).willReturn(Optional.empty());

        final NoMemberException noMemberException = Assertions.catchThrowableOfType(
                () -> modifyMemberProfileService.modifyMemberProfile(ModifyMemberProfileRequest.builder().build(), 1L),
                NoMemberException.class);

        assertThat(noMemberException).isNotNull();
    }

    @DisplayName("수정 요청 값의 검증이 실패했을 경우 에러")
    @Test
    void validatedHasError() {
        given(mockValidator.validate(any())).willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException validationException = Assertions.catchThrowableOfType(
                () -> modifyMemberProfileService.modifyMemberProfile(ModifyMemberProfileRequest.builder().build(), 1L),
                ValidationException.class);

        assertThat(validationException).isNotNull();
        assertThat(validationException.getErrors()).isNotEmpty();
    }

    @DisplayName("요청에 맞게 프로필 수정 확인")
    @Test
    void profileUpdate() {
        final Long givenMemberId = 1L;
        final Member givenMember = Member.builder()
                .id(givenMemberId)
                .loggedIn(new LoggedIn("loginId", "password"))
                .nickname("nickname")
                .profileImage("profileImage")
                .stateMessage("stateMessage")
                .status(MemberStatus.N)
                .build();
        given(mockValidator.validate(any())).willReturn(List.of());
        given(spyMemberRepository.findById(givenMemberId)).willReturn(Optional.of(givenMember));
        final String givenUpdateNickname = "updateNickname";
        final String givenUpdateProfileImage = "updateProfileImage";
        final String givenUpdateStateMessage = "updateStateMessage";
        final ModifyMemberProfileRequest givenRequest = ModifyMemberProfileRequest.builder()
                .nickname(givenUpdateNickname)
                .profileImage(givenUpdateProfileImage)
                .stateMessage(givenUpdateStateMessage)
                .build();

        modifyMemberProfileService.modifyMemberProfile(givenRequest, givenMemberId);

        assertThat(givenMember.getNickname()).isEqualTo(givenUpdateNickname);
        assertThat(givenMember.getProfileImage()).isEqualTo(givenUpdateProfileImage);
        assertThat(givenMember.getStateMessage()).isEqualTo(givenUpdateStateMessage);
    }
}