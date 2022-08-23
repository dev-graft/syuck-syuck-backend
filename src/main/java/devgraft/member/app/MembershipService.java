package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;
import devgraft.member.exception.AlreadyExistsLoginIdException;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private final MemberRepository memberRepository;
    private final MembershipRequestValidator membershipRequestValidator;

    @Transactional
    public MemberIds membership(final MembershipRequest request) {
        final List<ValidationError> errors = membershipRequestValidator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors, "회원가입 요청이 실패하였습니다");
        if (memberRepository.existsByLoginId(request.getLoginId())) throw new AlreadyExistsLoginIdException();

        final Member member = Member.of(request.getLoginId(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage(),
                "");

        memberRepository.save(member);

        return MemberIds.of(member.getId(), member.getLoginId());
    }
}
