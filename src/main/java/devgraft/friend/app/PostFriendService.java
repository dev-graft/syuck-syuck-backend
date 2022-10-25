package devgraft.friend.app;

import org.springframework.stereotype.Service;

@Service
public class PostFriendService {

    public void postFriend(final String memberId, final String target) {
        // TODO Member 도메인에 target 존재 확인 요청
        // TODO Member 도메인에 memberId의 친구 목록 불러오기
        // TODO 친구 목록에 존재하는지 & true인지 false인지 예외처리
        // TODO 친구 정보 추가
        // TODO 친구 요청 이벤트 발행
            // 타인라임용 이력 추가
            // 대상에게 푸쉬 메세지 발송
    }
}
