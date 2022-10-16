package devgraft.follow.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface FindMemberService {
    FindMemberResult findMember(final String memberId) throws NotFoundFollowTargetException;

    @AllArgsConstructor
    @Getter
    class FindMemberResult {
        private final String memberId;
        private final String nickname;
    }
}
