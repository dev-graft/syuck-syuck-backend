package devgraft.auth.infra;

import devgraft.auth.domain.MemberAuthenticationService;
import devgraft.member.domain.MemberMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberAuthenticationServiceImpl implements MemberAuthenticationService {
    private final MemberMatchService memberMatchService;

    @Override
    public AuthenticateMemberResult authenticate(final AuthenticateMemberRequest request) {
        final MemberMatchService.MemberMatchResult result = memberMatchService.match(MemberMatchService.MemberMatchRequest.of(request.getLoginId(), request.getPassword()));
        return new AuthenticateMemberResult(result.getMessage(), result.isSuccess());
    }
}
