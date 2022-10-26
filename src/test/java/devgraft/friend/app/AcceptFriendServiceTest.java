package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.SelfAcceptFriendException;
import devgraft.friend.app.exception.UnrelatedAcceptFriendException;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationFixture;
import devgraft.friend.domain.FriendRelationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AcceptFriendServiceTest {
    private AcceptFriendService acceptFriendService;
    private FriendRelationRepository mockFriendRelationRepository;
    private FriendEventSender mockFriendEventSender;

    @BeforeEach
    void setUp() {
        mockFriendRelationRepository = mock(FriendRelationRepository.class);
        mockFriendEventSender = mock(FriendEventSender.class);

        given(mockFriendRelationRepository.findById(any())).willReturn(Optional.of(FriendRelationFixture.anFriendRelation().areFriends(false).build()));

        acceptFriendService = new AcceptFriendService(mockFriendRelationRepository, mockFriendEventSender);
    }

    @DisplayName("친구관계 id 기준 조회 결과가 없음으로 예외처리")
    @Test
    void acceptFriend_throwNotFoundFriendRelation() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.empty());

        Assertions.assertThrows(NotFoundFriendRelationException.class, () ->
                acceptFriendService.acceptFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("이미 친구관계일 경우 예외처리")
    @Test
    void acceptFriend_throwAlreadyFriendRelationException() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(FriendRelationFixture.anFriendRelation()
                .receiver("memberId")
                .areFriends(true).build()));

        Assertions.assertThrows(AlreadyFriendRelationException.class, () ->
                acceptFriendService.acceptFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("자신이 요청한 친구관계를 스스로 승인하려 할 경우 예외처리")
    @Test
    void acceptFriend_throwSelfAcceptFriendException() {
        final String givenMemberId = "memberId";
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId))
                .willReturn(Optional.of(FriendRelationFixture.anFriendRelation().sender(givenMemberId).areFriends(false).build()));

        Assertions.assertThrows(SelfAcceptFriendException.class, () ->
                acceptFriendService.acceptFriend(givenMemberId, givenFriendRelationId));
    }

    @DisplayName("자신과 관계없는 친구요청을 요청했을 경우 예외처리")
    @Test
    void acceptFriend_throwUnrelatedAcceptFriendException() {
        final String givenMemberId = "memberId";
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId))
                .willReturn(Optional.of(FriendRelationFixture.anFriendRelation().areFriends(false).build()));

        Assertions.assertThrows(UnrelatedAcceptFriendException.class, () ->
                acceptFriendService.acceptFriend(givenMemberId, givenFriendRelationId));
    }

    @DisplayName("친구 요청 상태 승인으로 변경 후 저장")
    @Test
    void acceptFriend_callSaveToRepository() {
        final String givenMemberId = "memberId";
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId))
                .willReturn(Optional.of(FriendRelationFixture.anFriendRelation().id(givenFriendRelationId).receiver(givenMemberId).areFriends(false).build()));

        acceptFriendService.acceptFriend(givenMemberId, givenFriendRelationId);

        final FriendRelation correctFriendRelation = FriendRelationFixture.anFriendRelation()
                .id(givenFriendRelationId)
                .receiver(givenMemberId)
                .areFriends(true)
                .build();

        verify(mockFriendRelationRepository, times(1)).save(refEq(correctFriendRelation));
    }

    @DisplayName("친구 요청 수락 이벤트 발행")
    @Test
    void acceptFriend_PubEvent() {
        final String givenReceiver = "memberId";
        final Long givenFriendRelationId = 10L;
        final String givenSender = "g-sender";
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .areFriends(false)
                .id(givenFriendRelationId)
                .sender(givenSender)
                .receiver(givenReceiver)
                .build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId))
                .willReturn(Optional.of(givenFriendRelation));

        acceptFriendService.acceptFriend(givenReceiver, givenFriendRelationId);

        verify(mockFriendEventSender, times(1)).acceptFriend(eq(givenFriendRelationId), eq(givenSender), eq(givenReceiver));
    }
}