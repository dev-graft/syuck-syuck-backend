package devgraft.friend.app;

import devgraft.friend.app.exception.NotAlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.UnrelatedDeleteFriendException;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteFriendService {
    private final FriendRelationRepository friendRelationRepository;
    private final FriendEventSender friendEventSender;

    public void deleteFriend(final String memberId, final Long friendRelationId) {
        // friendRelationId 기준 조회 & 없을 경우 예외처리
        final FriendRelation friendRelation = friendRelationRepository.findById(friendRelationId).orElseThrow(NotFoundFriendRelationException::new);
        // 자신과 관계있는 친구관계 정보인지 검사 & 관계 없으면 예외처리
        if (!(friendRelation.compareSenderTo(memberId) || friendRelation.compareReceiverTo(memberId))) throw new UnrelatedDeleteFriendException();
        // 친구상태인지 검사
        if (!friendRelation.isAreFriends()) throw new NotAlreadyFriendRelationException();
        // 삭제
        final String target = friendRelation.compareSenderTo(memberId) ? friendRelation.getReceiver() : friendRelation.getSender();
        friendRelationRepository.delete(friendRelation);
        // event 발행(누가 삭제 요청했는지, 누가 관계가 끊겼는지)
        friendEventSender.deleteFriend(memberId, target);
    }
}
