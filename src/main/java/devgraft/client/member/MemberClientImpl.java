package devgraft.client.member;

import devgraft.follow.domain.NotFoundFollowTargetException;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberClientImpl implements MemberClient {
    private final MemberDataDao memberDataDao;

    @Override
    public FindMemberResult findMember(final String memberId) throws NotFoundFollowTargetException {
        final MemberData memberData = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(memberId).and(MemberDataSpec.normalEquals()))
                .orElseThrow(NotFoundFollowTargetException::new);

        return FindMemberResult.builder()
                .memberId(memberId)
                .nickname(memberData.getNickname())
                .profileImage(memberData.getProfileImage())
                .stateMessage(memberData.getStateMessage())
                .build();
    }
}
