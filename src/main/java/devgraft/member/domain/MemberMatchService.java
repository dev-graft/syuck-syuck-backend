package devgraft.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberMatchService {
    private final MemberCryptoService memberCryptoService;
    private final MemberRepository memberRepository;

    public MemberMatchResult match(final MemberMatchRequest request) {
        final Optional<Member> memberOpt = memberRepository.findById(MemberId.from(request.getLoginId()));
        if (memberOpt.isEmpty()) return MemberMatchResult.of("요청한 아이디를 찾지 못했습니다. 확인 바랍니다.", false);
        if (memberOpt.get().isLeave()) return MemberMatchResult.of("탈퇴된 회원입니다. 계정복구를 원할 경우 관리자에게 문의 바랍니다.", false);
        if (!memberOpt.get().isMatch(memberCryptoService, request.getPassword())) return MemberMatchResult.of("요청한 인증이 실패하였습니다. 패스워드를 확인해주세요.", false);

        return MemberMatchResult.of("성공", false);
    }
}
