package devgraft.follow.app;

import devgraft.follow.domain.AlreadyFollowingException;
import devgraft.follow.domain.FindMemberService;
import devgraft.follow.domain.Follow;
import devgraft.follow.domain.FollowEventSender;
import devgraft.follow.domain.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AskFollowService {
    private final FindMemberService findMemberService;
    private final FollowRepository followRepository;
    private final FollowEventSender followEventSender;

    public void askFollow(final String memberId, final String target) {
        findMemberService.findMember(target);
        if (Objects.equals(memberId, target)) throw new SelfFollowException();

        final Optional<Follow> followOpt = followRepository.findByFollowerIdAndFollowingId(memberId, target);

        if (followOpt.isPresent()) throw new AlreadyFollowingException();

        final Follow follow = Follow.builder()
                .followerId(memberId)
                .followingId(target)
                .build();
        followRepository.save(follow);

        followEventSender.askFollow(memberId, target);
    }
}
