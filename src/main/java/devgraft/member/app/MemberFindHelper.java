package devgraft.member.app;

import devgraft.member.domain.MemberId;
import devgraft.member.domain.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberFindHelper {

    public static boolean isExists(final String memberIdStr, final MemberRepository memberRepository) {
        final MemberId memberId = MemberId.from(memberIdStr);
        return memberRepository.existsById(memberId);
    }
}
