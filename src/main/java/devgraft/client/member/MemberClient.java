package devgraft.client.member;

import devgraft.follow.domain.NotFoundFollowTargetException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface MemberClient {
    FindMemberResult findMember(final String memberId) throws NotFoundFollowTargetException;

    @Builder
    @AllArgsConstructor
    @Getter
    class FindMemberResult {
        private final String memberId;
        private final String nickname;
        private final String profileImage;
        private final String stateMessage;
    }
}
