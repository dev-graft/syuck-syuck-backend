package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModifyMemberProfileService {
    private final MemberRepository memberRepository;
    private final ModifyMemberProfileRequestValidator validator;

    @Transactional
    public void modifyMemberProfile(final ModifyMemberProfileRequest request, final Long memberId) {
        List<ValidationError> errors = validator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors, "회원 수정 요청이 실패하였습니다.");

        final Member member = MemberFindHelper.findMember(memberRepository, memberId);

        member.setProfile(request.getNickname(), request.getStateMessage(), request.getProfileImage());
    }
}
