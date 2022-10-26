package devgraft.friend.app;

import devgraft.friend.app.exception.AlreadyFriendRelationException;
import devgraft.friend.app.exception.AlreadyPostFriendException;
import devgraft.friend.app.exception.NotFoundFriendTargetException;
import devgraft.friend.app.exception.SelfPostFriendException;
import devgraft.friend.domain.ExistsFriendTargetService;
import devgraft.friend.domain.FriendEventSender;
import devgraft.friend.domain.FriendRelation;
import devgraft.friend.domain.FriendRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostFriendService {
    private final ExistsFriendTargetService existsFriendTargetService;
    private final FriendRelationRepository friendRelationRepository;
    private final FriendEventSender friendEventSender;

    public void postFriend(final String memberId, final String target) {
        /* 자기자신에게 요청하는 것인지 확인 */
        if (Objects.equals(memberId, target)) throw new SelfPostFriendException();
        /* Member 도메인에 target 존재 확인 요청 & 없을 경우 예외처리 */
        if (!existsFriendTargetService.isExistsFriendTarget(target)) throw new NotFoundFriendTargetException();
        /* memberId와 target이 연관된 정보가 있는지 조회 */
        final Optional<FriendRelation> friendRelationOpt = friendRelationRepository.findFriendRelationBySenderOrReceiver(memberId, memberId).stream()
                .filter(friendRelation ->
                        Objects.equals(friendRelation.getReceiver(), target) || Objects.equals(friendRelation.getSender(), target))
                .findFirst();
        /* 조회 정보가 존재하고, 이미 요청 상태일 경우 예외처리 */
        if (friendRelationOpt.isPresent()) {
            if (friendRelationOpt.get().isAreFriends()) throw new AlreadyFriendRelationException();
            throw new AlreadyPostFriendException();
        }
        /* 친구 정보 생성 및 저장 */
        final FriendRelation friendRelation = FriendRelation.create(memberId, target);
        friendRelationRepository.save(friendRelation);
        /* 친구 요청 이벤트 발행(Friend, Post, Sender, Receiver, LocalDateTime) */
        friendEventSender.postFriend(friendRelation.getId(), memberId, target);
    }
}
