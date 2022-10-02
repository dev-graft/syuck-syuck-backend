package devgraft.member.app;

import devgraft.member.domain.AlreadyExistsMemberIdException;
import devgraft.member.domain.Member;
import devgraft.member.domain.MemberCryptoService;
import devgraft.member.domain.MemberRepository;
import devgraft.support.exception.ValidationError;
import devgraft.support.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignUpService {
    private final SignUpRequestDecoder signUpRequestDecoder;
    private final DecryptedSignUpDataValidator decryptedSignUpDataValidator;
    private final MemberRepository memberRepository;
    private final MemberProvider memberProvider;
    private final MemberCryptoService memberCryptoService;

    public void signUp(final EncryptedSignUpRequest request, final KeyPair keyPair) {
        final DecryptedSignUpData decryptedSignUpData = signUpRequestDecoder.decrypt(request, keyPair);
        final List<ValidationError> errors = decryptedSignUpDataValidator.validate(decryptedSignUpData);

        if (!errors.isEmpty()) throw new ValidationException(errors, "회원가입 요청이 실패하였습니다.");

        if (MemberFindHelper.isExists(decryptedSignUpData.getLoginId(), memberRepository)) {
            throw new AlreadyExistsMemberIdException();
        }

        final Member member = memberProvider.create(decryptedSignUpData);
        memberRepository.save(member);
    }

    public KeyPair generatedSignUpCode() {
        return memberCryptoService.generatedCryptKey();
    }
}
