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
public class MembershipService {
    private final MemberRepository memberRepository;
    private final MembershipRequestValidator membershipRequestValidator;

    @Transactional
    public MemberId membership(final MembershipRequest request) {
        List<ValidationError> errors = membershipRequestValidator.validate(request);
        if (!errors.isEmpty()) throw new ValidationException(errors);

        // 아이디 중복 체크

        Member member = Member.of(request.getMemberId(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage(),
                "");

        memberRepository.save(member);

        return MemberId.of(member.getIdx(), member.getId());
    }
}
