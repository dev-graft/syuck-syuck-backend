package devgraft.friend.app;

import devgraft.friend.domain.ExistsFriendTargetService;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationFixture;
import devgraft.friend.domain.FriendRelationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PostFriendServiceTest {
    private PostFriendService postFriendService;
    private ExistsFriendTargetService mockExistsFriendTargetService;
    private FriendRelationRepository mockFriendRelationRepository;
    private FriendEventSender mockFriendEventSender;
    @BeforeEach
    void setUp() {
        mockExistsFriendTargetService = mock(ExistsFriendTargetService.class);
        mockFriendRelationRepository = mock(FriendRelationRepository.class);
        mockFriendEventSender = mock(FriendEventSender.class);
        given(mockExistsFriendTargetService.isExistsFriendTarget(any())).willReturn(true);

        postFriendService = new PostFriendService(mockExistsFriendTargetService, mockFriendRelationRepository, mockFriendEventSender);
    }

    @DisplayName("자신에게 친구요청했을 경우 예외처리")
    @Test
    void postFriend_throwSelfPostFriendException() {
        assertThrows(SelfPostFriendException.class, () ->
                postFriendService.postFriend("memberId", "memberId"));
    }

    @DisplayName("[target 존재 확인]도메인 서비스 결과 존재하지 않을 시 예외처리")
    @Test
    void postFriend_throwNotFoundFriendTargetException() {
        final String givenTarget = "target";
        given(mockExistsFriendTargetService.isExistsFriendTarget(givenTarget)).willReturn(false);

        assertThrows(NotFoundFriendTargetException.class, () ->
                postFriendService.postFriend("memberId", givenTarget));
    }

    @DisplayName("이미 친구 요청을 보낸 상태일 경우 예외처리")
    @Test
    void postFriend_throwAlreadyPostFriendException() {
        final String givenMemberId = "memberId";
        final String givenTarget = "target";

        given(mockFriendRelationRepository.findFriendRelationBySenderOrReceiver(givenMemberId, givenMemberId))
                .willReturn(List.of(FriendRelationFixture.anFriendRelation().receiver(givenTarget).areFriends(false).build()));

        assertThrows(AlreadyPostFriendException.class, () ->
            postFriendService.postFriend(givenMemberId, givenTarget));
    }

    @DisplayName("이미 친구관계일 경우 예외처리")
    @Test
    void postFriend_throwAlreadyFriendRelationException() {
        final String givenMemberId = "memberId";
        final String givenTarget = "target";

        given(mockFriendRelationRepository.findFriendRelationBySenderOrReceiver(givenMemberId, givenMemberId))
                .willReturn(List.of(FriendRelationFixture.anFriendRelation().receiver(givenTarget).areFriends(true).build()));

        assertThrows(AlreadyFriendRelationException.class, () ->
                postFriendService.postFriend(givenMemberId, givenTarget));
    }

    @DisplayName("생성한 친구관계 정보를 Repository 저장")
    @Test
    void postFriend_callSaveToRepository() {
        final String givenMemberId = "memberId";
        final String givenTarget = "target";
        // 내가 테스트해야할 것은 특정 메서드나 클래스로 생성된 친구정보가 DB에 저장되었는지 확인하는 것.
        // 즉 친구정보의 데이터 내부가 어떻든 변경되든 상관 없이 호출 테스트다.
        final FriendRelation givenFriendRelation = FriendRelation.create(givenMemberId, givenTarget);
        postFriendService.postFriend(givenMemberId, givenTarget);

        verify(mockFriendRelationRepository, times(1)).save(refEq(givenFriendRelation));
    }

    @DisplayName("친구 요청 성공 이벤트 발행")
    @Test
    void postFriend_PubEvent() {
        final String givenMemberId = "memberId";
        final String givenTarget = "target";

        postFriendService.postFriend(givenMemberId, givenTarget);

        verify(mockFriendEventSender, times(1)).postFriend(any(), eq(givenMemberId), eq(givenTarget));
    }
}