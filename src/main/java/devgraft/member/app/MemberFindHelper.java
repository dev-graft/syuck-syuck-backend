package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;

public final class MemberFindHelper {
    public static Member findExistingMember(final MemberRepository repository, final Long memberId) {
        final Member member = repository.findById(memberId).orElseThrow(NoMemberException::new);
        if (member.isLeave()) throw new NoMemberException();
        return member;
    }
}