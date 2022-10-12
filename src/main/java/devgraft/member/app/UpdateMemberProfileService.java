package devgraft.member.app;

import devgraft.member.api.NotFoundMemberException;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberId;
import devgraft.member.domain.MemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateMemberProfileService {
    private final UpdateMemberProfileValidator updateMemberProfileValidator;
    private final MemberRepository memberRepository;

    public void update(final String memberId, final UpdateMemberProfileRequest request) {
        final List<ValidationError> errors = updateMemberProfileValidator.validate(request);
        if(!errors.isEmpty()) throw new ValidationException(errors, "프로필 업데이트 요청이 실패했습니다.");
        final Member member = memberRepository.findById(MemberId.from(memberId)).orElseThrow(NotFoundMemberException::new);
        member.updateProfile(request.getNickname(), request.getStateMessage());
        memberRepository.save(member);
    }
}
