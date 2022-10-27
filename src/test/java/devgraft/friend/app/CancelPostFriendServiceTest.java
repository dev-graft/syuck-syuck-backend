package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.UnrelatedCancelPostFriendException;
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

class CancelPostFriendServiceTest {
    private CancelPostFriendService cancelPostFriendService;
    private FriendRelationRepository mockFriendRelationRepository;
    private FriendEventSender mockFriendEventSender;

    @BeforeEach
    void setUp() {
        mockFriendRelationRepository = mock(FriendRelationRepository.class);
        mockFriendEventSender = mock(FriendEventSender.class);

        given(mockFriendRelationRepository.findById(any())).willReturn(Optional.of(FriendRelationFixture.anFriendRelation().areFriends(false).build()));

        cancelPostFriendService = new CancelPostFriendService(mockFriendRelationRepository, mockFriendEventSender);
    }

    @DisplayName("친구관계 id 기준 조회 결과가 없음으로 예외처리")
    @Test
    void cancelPostFriend_throwNotFoundFriendRelation() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.empty());

        Assertions.assertThrows(NotFoundFriendRelationException.class, () ->
                cancelPostFriendService.cancelPostFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("자신과 관계없는 친구요청에 대해 취소를 시도할 경우 예외처리")
    @Test
    void cancelPostFriend_throwUnrelatedCancelPostFriendException() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(FriendRelationFixture.anFriendRelation()
                .sender("dede")
                .areFriends(false).build()));

        Assertions.assertThrows(UnrelatedCancelPostFriendException.class, () ->
                cancelPostFriendService.cancelPostFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("이미 친구관계일 경우 예외처리")
    @Test
    void cancelPostFriend_throwAlreadyFriendRelationException() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(FriendRelationFixture.anFriendRelation()
                .sender("memberId")
                .areFriends(true).build()));

        Assertions.assertThrows(AlreadyFriendRelationException.class, () ->
                cancelPostFriendService.cancelPostFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("친구관계 정보를 Repository에서 제거")
    @Test
    void cancelPostFriend_callDeleteToRepository() {
        final long givenFriendRelationId = 10L;
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .sender("memberId")
                .areFriends(false).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        cancelPostFriendService.cancelPostFriend("memberId", givenFriendRelationId);

        verify(mockFriendRelationRepository, times(1)).delete(refEq(givenFriendRelation));
    }

    @DisplayName("친구 요청 취소 이벤트 발행")
    @Test
    void cancelPostFriend_PubEvent() {
        final long givenFriendRelationId = 10L;
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .sender("memberId")
                .areFriends(false).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        cancelPostFriendService.cancelPostFriend("memberId", givenFriendRelationId);

        verify(mockFriendEventSender, times(1)).cancelPostFriend(eq(givenFriendRelation.getSender()), eq(givenFriendRelation.getReceiver()));
    }
}