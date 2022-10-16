package devgraft.follow.app;

import devgraft.follow.domain.Follow;
import devgraft.follow.domain.FollowEventSender;
import devgraft.follow.domain.FollowRepository;
import devgraft.follow.domain.NotFoundFollowTargetException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CancelFollowService {
    private final FollowRepository followRepository;
    private final FollowEventSender followEventSender;

    public void cancelFollow(final String memberId, final String fId) {
        final Follow follow = followRepository.findByFollowerIdAndFollowingId(memberId, fId)
                .orElseThrow(NotFoundFollowTargetException::new);
        followRepository.delete(follow);
        followEventSender.cancelFollow(memberId, fId);
    }
}
