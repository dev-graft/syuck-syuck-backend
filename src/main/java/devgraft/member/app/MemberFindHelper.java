package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberFindHelper {
    public static Member findMember(final MemberRepository repository, final Long memberId) {
        final Member member = repository.findById(memberId).orElseThrow(NoMemberException::new);
        if (member.isLeave()) throw new NoMemberException();
        return member;
    }

    public static Member findMember(final MemberRepository repository, final String loginId) {
        final Member member = repository.findByLoggedId(loginId).orElseThrow(NoMemberException::new);
        if (member.isLeave()) throw new NoMemberException();
        return member;
    }
}