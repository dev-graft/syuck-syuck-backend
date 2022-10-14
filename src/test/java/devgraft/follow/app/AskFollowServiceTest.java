package devgraft.follow.app;

import devgraft.follow.domain.AlreadyFollowingException;
import devgraft.follow.domain.FindMemberService;
import devgraft.follow.domain.Follow;
import devgraft.follow.domain.FollowEventSender;
import devgraft.follow.domain.FollowFixture;
import devgraft.follow.domain.FollowRepository;
import devgraft.follow.domain.NotFoundFollowTargetException;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AskFollowServiceTest {
    private AskFollowService askFollowService;
    private AskFollowRequestValidator mockAskFollowRequestValidator;
    private FindMemberService mockFindMemberService;
    private FollowRepository mockFollowRepository;
    private FollowEventSender mockFollowEventSender;
    @BeforeEach
    void setUp() {
        mockAskFollowRequestValidator = mock(AskFollowRequestValidator.class);
        mockFindMemberService = mock(FindMemberService.class);
        mockFollowRepository = mock(FollowRepository.class);
        mockFollowEventSender = mock(FollowEventSender.class);

        askFollowService = new AskFollowService(mockAskFollowRequestValidator, mockFindMemberService, mockFollowRepository, mockFollowEventSender);
    }

    @DisplayName("팔로우 요청문 검증 에러 발생")
    @Test
    void askFollow_throwValidationException() {
        final AskFollowRequest givenRequest = AskFollowRequestFixture.anRequest().build();
        given(mockAskFollowRequestValidator.validate(givenRequest)).willReturn(List.of(ValidationError.of("field", "message")));

        final ValidationException errors = catchThrowableOfType(() ->
                askFollowService.askFollow("memberId", givenRequest), ValidationException.class);

        assertThat(errors).isNotNull();
        assertThat(errors.getErrors()).isNotEmpty();
    }

    @DisplayName("팔로우 요청 대상을 찾을 수 없을 경우 에러")
    @Test
    void askFollow_throwNotFoundFollowTargetException() {
        final AskFollowRequest givenRequest = AskFollowRequestFixture.anRequest().build();
        given(mockFindMemberService.findMember(givenRequest.getFollowMemberId()))
                .willThrow(new NotFoundFollowTargetException());

        Assertions.assertThrows(NotFoundFollowTargetException.class, () ->
        askFollowService.askFollow("memberId", givenRequest));
    }

    @DisplayName("이미 팔로우 하고 있을 경우 에러")
    @Test
    void askFollow_throwAlreadyFollowingException() {
        final String givenMemberId = "memberId";
        final String followingMemberId = "FFF_ID";
        final AskFollowRequest givenRequest = AskFollowRequestFixture.anRequest().followMemberId(followingMemberId).build();
        given(mockFollowRepository.findByMemberIdAndFollowingMemberId(givenMemberId, followingMemberId)).willReturn(Optional.of(FollowFixture.anFollow().memberId(givenMemberId).followingMemberId(followingMemberId).build()));

        Assertions.assertThrows(AlreadyFollowingException.class, () ->
                askFollowService.askFollow(givenMemberId, givenRequest));
    }

    @DisplayName("팔로우 정보 저장")
    @Test
    void follow_callSaveToRepository() {
        final String givenMemberId = "memberId";
        final AskFollowRequest givenRequest = AskFollowRequestFixture.anRequest().build();
        final Follow mockFollow = FollowFixture.anFollow().memberId(givenMemberId).followingMemberId(givenRequest.getFollowMemberId()).build();

        askFollowService.askFollow(givenMemberId, givenRequest);

        verify(mockFollowRepository, times(1)).save(refEq(mockFollow));
    }

    @DisplayName("팔로우 성공 이벤트 던지기")
    @Test
    void follow_callFollowingToFollowSender() {
        final String givenMemberId = "memberId";
        final AskFollowRequest givenRequest = AskFollowRequestFixture.anRequest().build();

        askFollowService.askFollow(givenMemberId, givenRequest);

        verify(mockFollowEventSender, times(1)).askFollow(givenMemberId, givenRequest.getFollowMemberId());
    }
}