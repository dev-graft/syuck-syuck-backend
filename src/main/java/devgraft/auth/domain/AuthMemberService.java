package devgraft.auth.domain;

public interface AuthMemberService {
    AuthenticateMemberResult authenticate(AuthenticateMemberRequest request);
}
