package devgraft.follow.app;

import devgraft.follow.domain.Follow;
import devgraft.follow.domain.FollowEventSender;
import devgraft.follow.domain.FollowFixture;
import devgraft.follow.domain.FollowRepository;
import devgraft.follow.domain.NotFoundFollowTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CancelFollowServiceTest {
    private CancelFollowService cancelFollowService;
    private FollowRepository mockFollowRepository;
    private FollowEventSender mockFollowEventSender;

    @BeforeEach
    void setUp() {
        mockFollowRepository = Mockito.mock(FollowRepository.class);
        mockFollowEventSender = Mockito.mock(FollowEventSender.class);
        cancelFollowService = new CancelFollowService(mockFollowRepository, mockFollowEventSender);
    }

    @DisplayName("자신의 팔로우 목록에서 취소 대상을 찾지 못했을 시 에러")
    @Test
    void cancelFollow_throwNotFoundFollowTargetException() {
        final String givenMemberId = "memberId";
        final String followingMemberId = "FFF_ID";

        assertThrows(NotFoundFollowTargetException.class, () ->
                cancelFollowService.cancelFollow(givenMemberId, followingMemberId));

        verify(mockFollowRepository, times(1)).findByMemberIdAndFollowingMemberId(givenMemberId, followingMemberId);
    }

    @DisplayName("팔로우 정보 삭제")
    @Test
    void cancelFollow_callDeleteToRepository() {
        final String givenMemberId = "memberId";
        final String givenFId = "fId";
        final Follow givenFollow = FollowFixture.anFollow().memberId(givenMemberId).followingMemberId(givenFId).build();
        given(mockFollowRepository.findByMemberIdAndFollowingMemberId(givenMemberId, givenFId)).willReturn(Optional.of(givenFollow));

        cancelFollowService.cancelFollow(givenMemberId, givenFId);

        verify(mockFollowRepository, times(1)).delete(refEq(givenFollow));
    }

    @DisplayName("팔로우 삭제 이벤트 올리기")
    @Test
    void cancelFollow_sendCancelEvent() {
        final String givenMemberId = "memberId";
        final String givenFId = "fId";
        final Follow givenFollow = FollowFixture.anFollow().memberId(givenMemberId).followingMemberId(givenFId).build();
        given(mockFollowRepository.findByMemberIdAndFollowingMemberId(givenMemberId, givenFId)).willReturn(Optional.of(givenFollow));

        cancelFollowService.cancelFollow(givenMemberId, givenFId);

        verify(mockFollowEventSender, times(1)).cancelFollow(givenMemberId, givenFId);
    }
}