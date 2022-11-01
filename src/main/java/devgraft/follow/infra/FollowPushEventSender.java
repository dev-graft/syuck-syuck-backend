package devgraft.follow.infra;

import devgraft.follow.domain.FollowEventSender;
import devgraft.support.event.EventCode;
import devgraft.support.event.EventInterface;
import devgraft.support.event.Events;
import org.springframework.stereotype.Component;

@Component
public class FollowPushEventSender implements FollowEventSender {
    @Override
    public void askFollow(final String memberId, final String followingId) {
        Events.raise(new AskFollowEvent());
    }

    @Override
    public void cancelFollow(final String memberId, final String cancelFollowId) {
        Events.raise(new CancelFollowEvent());

    }

    public static class AskFollowEvent implements EventInterface {
        public AskFollowEvent() {
        }

        @Override
        public EventCode getEventCode() {
            return null;
        }
    }

    public static class CancelFollowEvent implements EventInterface {
        public CancelFollowEvent() {
        }

        @Override
        public EventCode getEventCode() {
            return null;
        }
    }

    // 핸들러
    // 팔로우 이벤트 수신
    // A가 B를 팔로우 했다?
    // A가 당신을 팔로우 했다?
    // 대체 뭘 알고 싶을까...
    // 정확히 하기 전 까지는 그냥 Event만 발생시키자
}
