package devgraft.auth.infra;

import devgraft.auth.domain.AuthMemberService;
import devgraft.auth.domain.AuthenticateMemberRequest;
import devgraft.auth.domain.AuthenticateMemberResult;
import devgraft.member.domain.MemberMatchRequest;
import devgraft.member.domain.MemberMatchResult;
import devgraft.member.domain.MemberMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthMemberServiceImpl implements AuthMemberService {
    private final MemberMatchService memberMatchService;

    @Override
    public AuthenticateMemberResult authenticate(final AuthenticateMemberRequest request) {
        final MemberMatchResult result = memberMatchService.match(MemberMatchRequest.of(request.getLoginId(), request.getPassword()));
        return new AuthenticateMemberResult(result.getMessage(), result.isSuccess());
    }
}
