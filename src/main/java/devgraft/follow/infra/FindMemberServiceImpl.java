package devgraft.follow.infra;

import devgraft.follow.domain.FindMemberService;
import devgraft.follow.domain.NotFoundFollowTargetException;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindMemberServiceImpl implements FindMemberService {
    private final MemberDataDao memberDataDao;
    @Override
    public FindMemberResult findMember(final String memberId) {
        final MemberData memberData = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(memberId).and(MemberDataSpec.normalEquals()))
                .orElseThrow(NotFoundFollowTargetException::new);
        return new FindMemberResult(memberData.getMemberId(), memberData.getNickname());
    }
}
