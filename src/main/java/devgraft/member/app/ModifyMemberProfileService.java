package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ModifyMemberProfileService {
    private final MemberRepository memberRepository;

    public void modifyMemberProfile(final ModifyMemberProfileRequest request, final Long memberId) {
        final Member member = MemberServiceHelper.findExistingMember(memberRepository, memberId);

        // 정규식 검사

        member.setProfile(request.getNickname(), request.getStateMessage(), request.getProfileImage());
    }
}
