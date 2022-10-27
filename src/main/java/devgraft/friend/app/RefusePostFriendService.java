package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.UnrelatedRefusePostFriendException;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefusePostFriendService {
    private final FriendRelationRepository friendRelationRepository;
    private final FriendEventSender friendEventSender;

    public void refusePostFriend(final String memberId, final Long friendRelationId) {
        // friendRelationId 기준 조회 & 없을 경우 예외처리
        final FriendRelation friendRelation = friendRelationRepository.findById(friendRelationId).orElseThrow(NotFoundFriendRelationException::new);
        // receiver와 memberId가 동일한지 검사 & 틀리면 예외처리
        if (!friendRelation.compareReceiverTo(memberId)) throw new UnrelatedRefusePostFriendException();
        // 이미 친구가 승인되었는지 검사 & 승인되었다면 예외처리
        if(friendRelation.isAreFriends()) throw new AlreadyFriendRelationException();
        // Entity 삭제 처리
        final String sender = friendRelation.getSender();
        friendRelationRepository.delete(friendRelation);
        // 이벤트 발행
        friendEventSender.refusePostFriend(sender, memberId);
    }

}