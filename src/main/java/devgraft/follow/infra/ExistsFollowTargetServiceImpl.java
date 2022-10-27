package devgraft.follow.infra;

import devgraft.client.member.MemberClient;
import devgraft.follow.domain.ExistsFollowTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExistsFollowTargetServiceImpl implements ExistsFollowTargetService {
    private final MemberClient memberClient;

    @Override
    public boolean isExistsFollowTarget(final String target) {
        try {
            memberClient.findMember(target);
        }catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
