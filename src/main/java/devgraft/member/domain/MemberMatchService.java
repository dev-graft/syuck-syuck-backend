package devgraft.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

        return MemberMatchResult.of("성공", true);
    }

    @Getter
    public static class MemberMatchRequest {
        private final String loginId;
        private final String password;

        private MemberMatchRequest(final String loginId, final String password) {
            Assert.notNull(loginId, "MemberMatchRequest.loginId must not be null");
            Assert.notNull(password, "MemberMatchRequest.password must not be null");
            this.loginId = loginId;
            this.password = password;
        }

        public static MemberMatchRequest of(final String loginId, final String password) {
            return new MemberMatchRequest(loginId, password);
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MemberMatchResult {
        private final String message;
        private final boolean success;

        public static MemberMatchResult of(final String message, final boolean success) {
            return new MemberMatchResult(message, success);
        }
    }
}
