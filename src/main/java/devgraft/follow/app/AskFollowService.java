package devgraft.follow.app;

import org.springframework.stereotype.Service;

@Service
public class AskFollowService {
    public void askFollow(final String memberId, final AskFollowRequest request) {
        // askFollowRequest 검증
        // askFollowRequest.followerMemberId 대상 조회
        // Follow 정보 추가
        // Follow 추가 이벤트 호출
    }
}
