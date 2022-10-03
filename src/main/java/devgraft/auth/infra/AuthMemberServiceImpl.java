package devgraft.auth.infra;

import devgraft.auth.domain.AuthMemberService;
import devgraft.auth.domain.AuthenticateMemberRequest;
import devgraft.auth.domain.AuthenticateMemberResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthMemberServiceImpl implements AuthMemberService {
    @Override
    public AuthenticateMemberResult authenticate(final AuthenticateMemberRequest request) {
        return null;
    }
}
