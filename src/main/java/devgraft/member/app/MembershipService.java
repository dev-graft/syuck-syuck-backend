package devgraft.member.app;

import devgraft.member.domain.Member;
import devgraft.member.domain.MemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private final MemberRepository memberRepository;
    private final MembershipRequestValidator membershipRequestValidator;
    private final MemberPasswordHelper memberPasswordHelper;

    @Transactional
    public MemberIds membership(final MembershipRequest request, final KeyPair keyPair) {
        final String plainPassword = memberPasswordHelper.decryptPassword(request.getPassword(), keyPair);
        final List<ValidationError> errors = membershipRequestValidator.validate(MembershipRequest.builder()
                        .loginId(request.getLoginId())
                        .password(plainPassword)
                        .nickname(request.getNickname())
                        .profileImage(request.getProfileImage())
                .build());
        if (!errors.isEmpty()) throw new ValidationException(errors, "회원가입 요청이 실패하였습니다");
        if (memberRepository.existsByNickname(request.getLoginId())) throw new AlreadyExistsLoginIdException();

        final Member member = Member.of(request.getLoginId(),
                memberPasswordHelper.hashingPassword(request.getPassword()),
                request.getNickname(),
                request.getProfileImage(),
                "");

        memberRepository.save(member);

        return MemberIds.of(member.getId(), member.getLoggedIn().getLoggedId());
    }
}
