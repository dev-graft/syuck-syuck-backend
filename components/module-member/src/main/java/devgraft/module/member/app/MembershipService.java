package devgraft.module.member.app;

import devgraft.module.member.domain.Member;
import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MemberId;
import devgraft.module.member.domain.MemberRepository;
import devgraft.module.member.domain.MemberStatus;
import devgraft.module.member.domain.Password;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.KeyPair;
import java.util.List;

import static devgraft.module.member.domain.MemberConstant.MEMBERSHIP_FAILURE;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private final DecryptMembershipRequestProvider decryptMembershipRequestProvider;
    private final MemberCryptService memberCryptService;
    private final DecryptMembershipRequestValidator decryptMembershipRequestValidator;
    private final MemberRepository memberRepository;
    private final ProfileImageProvider profileImageProvider;

    @Transactional
    public void membership(final EncryptMembershipRequest request, final KeyPair keyPair) {
        final DecryptMembershipRequest deRequest = decryptMembershipRequestProvider.from(request, keyPair);
        final List<ValidationError> errors = decryptMembershipRequestValidator.validate(deRequest);
        if (!errors.isEmpty()) throw new ValidationException(errors, MEMBERSHIP_FAILURE);

        final MemberId memberId = MemberId.from(deRequest.getLoginId());
        if (memberRepository.existsById(memberId)) throw new AlreadyExistsMemberIdException();

        final Password password = memberCryptService.hashingPassword(deRequest.getPassword());
        final Member member = Member.of(memberId,
                password,
                deRequest.getNickname(),
                StringUtils.hasText(deRequest.getProfileImage()) ? deRequest.getProfileImage() : profileImageProvider.create(),
                "",
                MemberStatus.N);

        memberRepository.save(member);
    }
}
