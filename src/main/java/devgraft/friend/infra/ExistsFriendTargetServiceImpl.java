package devgraft.friend.infra;

import devgraft.client.member.MemberClient;
import devgraft.friend.domain.ExistsFriendTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExistsFriendTargetServiceImpl implements ExistsFriendTargetService {
    private final MemberClient memberClient;

    @Override
    public boolean isExistsFriendTarget(final String target) {
        try {
            memberClient.findMember(target);
        }catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
