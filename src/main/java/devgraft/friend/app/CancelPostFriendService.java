package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.UnrelatedCancelPostFriendException;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CancelPostFriendService {
    private final FriendRelationRepository friendRelationRepository;
    private final FriendEventSender friendEventSender;

    public void cancelPostFriend(final String memberId, final Long friendRelationId) {
        // friendRelationId 기준 조회 & 없을 경우 예외처리
        final FriendRelation friendRelation = friendRelationRepository.findById(friendRelationId).orElseThrow(NotFoundFriendRelationException::new);
        // 내가 요청자가 맞는지 검사
        if (!friendRelation.compareSenderTo(memberId)) throw new UnrelatedCancelPostFriendException();
        // 이미 친구상태인지 검사
        if(friendRelation.isAreFriends()) throw new AlreadyFriendRelationException();
        // 조회된 Entity 제거
        friendRelationRepository.delete(friendRelation);
        // 이벤트 발행
        friendEventSender.cancelPostFriend(friendRelation.getSender(), friendRelation.getReceiver());
    }
}
