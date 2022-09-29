package devgraft.module.member.app;

import devgraft.module.member.domain.Member;
import devgraft.module.member.domain.MemberCryptService;
import devgraft.module.member.domain.MemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.util.List;

import static devgraft.module.member.domain.MemberConstant.MEMBERSHIP_FAILURE;

@RequiredArgsConstructor
@Service
public class MembershipService {
    private final MembershipDecryptedDataProvider membershipDecryptedDataProvider;
    private final MemberCryptService memberCryptService;
    private final MembershipDecryptedDataValidator membershipDecryptedDataValidator;
    private final MemberRepository memberRepository;
    private final MemberProvider memberProvider;

    @Transactional
    public void membership(final EncryptMembershipRequest request, final KeyPair keyPair) {
        final MembershipDecryptedData decryptedData = membershipDecryptedDataProvider.create(memberCryptService, request, keyPair);

        final List<ValidationError> errors = membershipDecryptedDataValidator.validate(decryptedData);
        if (!errors.isEmpty()) throw new ValidationException(errors, MEMBERSHIP_FAILURE);

        final Member member = memberProvider.create(memberCryptService, decryptedData);

        if (memberRepository.existsById(member.getId())) throw new AlreadyExistsMemberIdException();

        memberRepository.save(member);
    }
}
