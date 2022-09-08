package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;

import java.util.Objects;

public class MemberFindHelper {
    public static Member findMember(final MemberRepository repository, final Long memberId) {
        final Member member = repository.findById(memberId).orElseThrow(NoMemberException::new);
        if (member.isLeave()) throw new NoMemberException();
        return member;
    }

    public static Member findMember(final MemberRepository repository, final String loginId) {
        final Member member = repository.streamAllBy()
                .filter(member1 -> Objects.equals(loginId, member1.getLoggedIn().getLoggedId()))
                .filter(member1 -> !member1.isLeave())
                .findFirst()
                .orElseThrow(NoMemberException::new);
        if (member.isLeave()) throw new NoMemberException();
        return member;
    }
}