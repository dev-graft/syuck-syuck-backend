package devgraft.follow.app;

import devgraft.follow.domain.AlreadyFollowingException;
import devgraft.follow.domain.FindMemberService;
import devgraft.follow.domain.Follow;
import devgraft.follow.domain.FollowEventSender;
import devgraft.follow.domain.FollowRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AskFollowService {
    private final AskFollowRequestValidator askFollowRequestValidator;
    private final FindMemberService findMemberService;
    private final FollowRepository followRepository;
    private final FollowEventSender followEventSender;

    public void askFollow(final String memberId, final AskFollowRequest request) {
        final List<ValidationError> errors = askFollowRequestValidator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors, "팔로우 요청이 올바르지 않습니다.");
        findMemberService.findMember(request.getFollowerLoginId());

        final Optional<Follow> followOpt = followRepository.streamAllByMemberId(memberId)
                .filter(follow -> follow.isSame(request.getFollowerLoginId()))
                .findFirst();
        if (followOpt.isPresent()) throw new AlreadyFollowingException();

        final Follow follow = Follow.builder()
                .memberId(memberId)
                .followingMemberId(request.getFollowerLoginId())
                .build();
        followRepository.save(follow);

        followEventSender.askFollow(memberId, request.getFollowerLoginId());
    }
}
