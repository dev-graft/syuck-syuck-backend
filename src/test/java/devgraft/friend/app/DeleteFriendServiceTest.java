package devgraft.friend.app;

import devgraft.friend.app.exception.NotAlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.UnrelatedDeleteFriendException;
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

class DeleteFriendServiceTest {
    private DeleteFriendService deleteFriendService;

    private FriendRelationRepository mockFriendRelationRepository;
    private FriendEventSender mockFriendEventSender;

    @BeforeEach
    void setUp() {
        mockFriendRelationRepository = mock(FriendRelationRepository.class);
        mockFriendEventSender = mock(FriendEventSender.class);

        given(mockFriendRelationRepository.findById(any())).willReturn(Optional.of(FriendRelationFixture.anFriendRelation().areFriends(true).build()));

        deleteFriendService = new DeleteFriendService(mockFriendRelationRepository, mockFriendEventSender);
    }

    @DisplayName("친구관계 id 기준 조회 결과가 없음으로 예외처리")
    @Test
    void deleteFriend_throwNotFoundFriendRelation() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.empty());

        Assertions.assertThrows(NotFoundFriendRelationException.class, () ->
                deleteFriendService.deleteFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("자신과 관계없는 친구관계에 대해 삭제를 시도할 경우 예외처리")
    @Test
    void deleteFriend_throwUnrelatedCancelPostFriendException() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(FriendRelationFixture.anFriendRelation()
                .sender("dede")
                .receiver("rrr")
                .areFriends(true).build()));

        Assertions.assertThrows(UnrelatedDeleteFriendException.class, () ->
                deleteFriendService.deleteFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("친구관계가 false 경우 예외처리")
    @Test
    void deleteFriend_throwNotAlreadyFriendRelationException() {
        final long givenFriendRelationId = 10L;
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(FriendRelationFixture.anFriendRelation()
                .sender("memberId")
                .areFriends(false).build()));

        Assertions.assertThrows(NotAlreadyFriendRelationException.class, () ->
                deleteFriendService.deleteFriend("memberId", givenFriendRelationId));
    }

    @DisplayName("친구관계 정보를 Repository에서 제거")
    @Test
    void deleteFriend_callDeleteToRepository() {
        final long givenFriendRelationId = 10L;
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .sender("memberId")
                .areFriends(true).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        deleteFriendService.deleteFriend("memberId", givenFriendRelationId);

        verify(mockFriendRelationRepository, times(1)).delete(refEq(givenFriendRelation));
    }

    @DisplayName("친구 관계 삭제 이벤트 발행")
    @Test
    void deleteFriend_PubEvent() {
        final long givenFriendRelationId = 10L;
        final String issuer = "memberId";
        final String target = "target";
        final FriendRelation givenFriendRelation = FriendRelationFixture.anFriendRelation()
                .sender(target)
                .receiver(issuer)
                .areFriends(true).build();
        given(mockFriendRelationRepository.findById(givenFriendRelationId)).willReturn(Optional.of(givenFriendRelation));

        deleteFriendService.deleteFriend(issuer, givenFriendRelationId);

        verify(mockFriendEventSender, times(1)).deleteFriend(eq(issuer), eq(target));
    }
}