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
        // 복호화
        final LoginDecryptedData loginDecryptedData = loginDecryptedDataProvider.create(memberCryptService, request, keyPair);
        // 검증
        final List<ValidationError> errors = loginDecryptedDataValidator.validate(loginDecryptedData);
        if (!errors.isEmpty()) throw new ValidationException(errors, LOGIN_FAILURE);
        // 조회(아이디, 상태-N기준)
        final Member member = MemberFindHelper.findMember(memberRepository, loginDecryptedData.getLoginId());
        // 패스워드 비교 -- 인증 통과
        if (!member.isMatch(memberCryptService, loginDecryptedData.getPassword())) throw new PasswordNotMatchException();
        // 인가 정보 생성
        // 포맷 추가 후 반환
    }
}
