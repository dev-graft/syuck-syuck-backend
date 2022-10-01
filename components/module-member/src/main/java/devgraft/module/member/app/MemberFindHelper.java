package devgraft.module.member.app;

import devgraft.module.member.domain.Member;
import devgraft.module.member.domain.MemberId;
import devgraft.module.member.domain.MemberRepository;

public class MemberFindHelper {

    public static Member findMember(final MemberRepository memberRepository, final String memberIdStr) {
        final MemberId memberId = MemberId.from(memberIdStr);
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        if (member.isLeave()) throw new LeaveMemberException();

        return member;
    }
}
