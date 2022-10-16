package devgraft.follow.app;

import devgraft.follow.domain.AlreadyFollowingException;
import devgraft.follow.domain.FindMemberService;
import devgraft.follow.domain.Follow;
import devgraft.follow.domain.FollowEventSender;
import devgraft.follow.domain.FollowFixture;
import devgraft.follow.domain.FollowRepository;
import devgraft.follow.domain.NotFoundFollowTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AskFollowServiceTest {
    private AskFollowService askFollowService;
    private FindMemberService mockFindMemberService;
    private FollowRepository mockFollowRepository;
    private FollowEventSender mockFollowEventSender;
    @BeforeEach
    void setUp() {
        mockFindMemberService = mock(FindMemberService.class);
        mockFollowRepository = mock(FollowRepository.class);
        mockFollowEventSender = mock(FollowEventSender.class);

        askFollowService = new AskFollowService(mockFindMemberService, mockFollowRepository, mockFollowEventSender);
    }

    @DisplayName("자기자신에게 팔로우 요청 시 에러")
    @Test
    void askFollow_throwSelfFollowException() {
        final String givenMemberId = "memberId";

        Assertions.assertThrows(SelfFollowException.class, () ->
                askFollowService.askFollow(givenMemberId, givenMemberId));
    }

    @DisplayName("팔로우 요청 대상을 찾을 수 없을 경우 에러")
    @Test
    void askFollow_throwNotFoundFollowTargetException() {
        given(mockFindMemberService.findMember("targetId"))
                .willThrow(new NotFoundFollowTargetException());

        Assertions.assertThrows(NotFoundFollowTargetException.class, () ->
        askFollowService.askFollow("memberId", "targetId"));
    }

    @DisplayName("이미 팔로우 하고 있을 경우 에러")
    @Test
    void askFollow_throwAlreadyFollowingException() {
        final String givenMemberId = "memberId";
        final String targetId = "FFF_ID";
        given(mockFollowRepository.findByFollowerIdAndFollowingId(givenMemberId, targetId)).willReturn(Optional.of(FollowFixture.anFollow().followerId(givenMemberId)
                .followingId(targetId).build()));

        Assertions.assertThrows(AlreadyFollowingException.class, () ->
                askFollowService.askFollow(givenMemberId, targetId));
    }

    @DisplayName("팔로우 정보 저장")
    @Test
    void follow_callSaveToRepository() {
        final String givenMemberId = "memberId";
        final String targetId = "targetId";
        final Follow mockFollow = FollowFixture.anFollow().followerId(givenMemberId).followingId(targetId).build();

        askFollowService.askFollow(givenMemberId, targetId);

        verify(mockFollowRepository, times(1)).save(refEq(mockFollow));
    }

    @DisplayName("팔로우 성공 이벤트 던지기")
    @Test
    void follow_callFollowingToFollowSender() {
        final String givenMemberId = "memberId";
        final String targetId = "targetId";

        askFollowService.askFollow(givenMemberId, targetId);

        verify(mockFollowEventSender, times(1)).askFollow(givenMemberId, targetId);
    }
}