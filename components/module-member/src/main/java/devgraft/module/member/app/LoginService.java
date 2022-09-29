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

import static devgraft.module.member.domain.MemberConstant.LOGIN_FAILURE;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberCryptService memberCryptService;
    private final LoginDecryptedDataProvider loginDecryptedDataProvider;
    private final LoginDecryptedDataValidator loginDecryptedDataValidator;
    private final MemberRepository memberRepository;

    @Transactional
    public void login(final EncryptLoginRequest request, final KeyPair keyPair) {
        final LoginDecryptedData loginDecryptedData = loginDecryptedDataProvider.create(memberCryptService, request, keyPair);

        final List<ValidationError> errors = loginDecryptedDataValidator.validate(loginDecryptedData);
        if (!errors.isEmpty()) throw new ValidationException(errors, LOGIN_FAILURE);

        final Member member = MemberFindHelper.findMember(memberRepository, loginDecryptedData.getLoginId());

        if (!member.match(memberCryptService, loginDecryptedData.getPassword())) throw new PasswordNotMatchException();


    }
}
