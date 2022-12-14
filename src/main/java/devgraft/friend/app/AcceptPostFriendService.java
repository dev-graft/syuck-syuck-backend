package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.NotFoundFriendRelationException;
import devgraft.friend.app.exception.SelfAcceptPostFriendException;
import devgraft.friend.app.exception.UnrelatedAcceptPostFriendException;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AcceptPostFriendService {
    private final FriendRelationRepository friendRelationRepository;
    private final FriendEventSender friendEventSender;

    public void acceptPostFriend(final String memberId, final Long friendRelationId) {
        // friendRelationId 기준 조회 & 없을 경우 예외처리
        final FriendRelation friendRelation = friendRelationRepository.findById(friendRelationId).orElseThrow(NotFoundFriendRelationException::new);
        // sender가 memberId인지 검사 & 맞을 경우 예외처리
        if (friendRelation.compareSenderTo(memberId)) throw new SelfAcceptPostFriendException();
        // receiver가 memberId인지 검사 & 아닐 경우 예외처리
        if (!friendRelation.compareReceiverTo(memberId)) throw new UnrelatedAcceptPostFriendException();
        // 이미 친구관계 상태인지 확인 << 해당 검증부터 진행하면 타인이 누구와 친구상태인지 유추할 수 있어서 먼저 하면 안된다.
        if (friendRelation.isAreFriends()) throw new AlreadyFriendRelationException();
        // 친구요청 상태 승인으로 변경 & 저장
        friendRelation.acceptFriendRequest();
        friendRelationRepository.save(friendRelation);
        // 이벤트 호출
        friendEventSender.acceptPostFriend(friendRelation.getId(), friendRelation.getReceiver(), friendRelation.getSender());
    }
}