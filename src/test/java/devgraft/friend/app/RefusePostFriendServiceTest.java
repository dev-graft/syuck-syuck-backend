package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.UnrelatedRefusePostFriendException;
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

class RefusePostFriendServiceTest {
    private  RefusePostFriendService refusePostFriendService;
    private FriendRelationRepository mockFriendRelationRepository;
    private FriendEventSender mockFriendEventSender;

    @BeforeEach
    void setUp() {
        mockFriendRelationRepository = mock(FriendRelationRepository.class);
        mockFriendEventSender = mock(FriendEventSender.class);

        given(mockFriendRelationRepository.findById(any())).willReturn(Optional.of(FriendRelationFixture.anFriendRelation().areFriends(false).build()));

        refusePostFriendService = new RefusePostFriendService(mockFriendRelationRepository, mockFriendEventSender);
    }

    @DisplayName("친구관계 id 기준 조회 결과가 없음으로 예외처리")
    @Test
    void refusePostFriend_throwNotFoundFriendRelation() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.empty());

        Assertions.assertThrows(NotFoundFriendRelationException.class, () ->
                refusePostFriendService.refusePostFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("자신과 관계없는 친구요청에 대해 취소를 시도할 경우 예외처리")
    @Test
    void refusePostFriend_throwUnrelatedRefusePostFriendException() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(FriendRelationFixture.anFriendRelation()
                .receiver("dede")
                .areFriends(false).build()));

        Assertions.assertThrows(UnrelatedRefusePostFriendException.class, () ->
                refusePostFriendService.refusePostFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("이미 친구관계일 경우 예외처리")
    @Test
    void refusePostFriend_throwAlreadyFriendRelationException() {
        final long givenFriendRelationId = 10L;
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .areFriends(true).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        Assertions.assertThrows(AlreadyFriendRelationException.class, () ->
                refusePostFriendService.refusePostFriend(givenFriendRelation.getReceiver(), givenFriendRelationId));
    }

    @DisplayName("친구관계 정보를 Repository에서 제거")
    @Test
    void cancelPostFriend_callDeleteToRepository() {
        final long givenFriendRelationId = 10L;
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .areFriends(false).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        refusePostFriendService.refusePostFriend(givenFriendRelation.getReceiver(), givenFriendRelationId);

        verify(mockFriendRelationRepository, times(1)).delete(refEq(givenFriendRelation));
    }

    @DisplayName("친구 요청 거절 이벤트 발행")
    @Test
    void cancelPostFriend_PubEvent() {
        final long givenFriendRelationId = 10L;
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .receiver("memberId")
                .areFriends(false).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        refusePostFriendService.refusePostFriend(givenFriendRelation.getReceiver(), givenFriendRelationId);

        verify(mockFriendEventSender, times(1)).refusePostFriend(eq(givenFriendRelation.getSender()), eq(givenFriendRelation.getReceiver()));
    }
}