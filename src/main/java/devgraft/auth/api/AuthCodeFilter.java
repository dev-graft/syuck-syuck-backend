package devgraft.auth.api;

import devgraft.auth.api.AuthCodeIOService.AuthorizationCode;
import devgraft.auth.app.AuthCodeVerificationService;
import devgraft.auth.app.BlockAuthSessionException;
import devgraft.auth.app.NotFoundAuthSessionException;
import devgraft.auth.query.AuthSessionData;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.auth.query.AuthSessionDataSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthCodeFilter extends OncePerRequestFilter {
    private final AuthCodeVerificationService authCodeVerificationService;
    private final AuthSessionDataDao authSessionDataDao;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final Optional<AuthorizationCode> authorizationCodeOpt = AuthCodeIOService.exportAuthorizationCode(request);
        if (authorizationCodeOpt.isPresent()) {
            final AuthorizationCode authorizationCode = authorizationCodeOpt.get();
            final String uniqId = authCodeVerificationService.verify(authorizationCode);
            final AuthSessionData authSessionData = authSessionDataDao.findOne(AuthSessionDataSpec.memberIdEquals(uniqId)).orElseThrow(NotFoundAuthSessionException::new);
            if (authSessionData.isBlock()) throw new BlockAuthSessionException();

            request.setAttribute("M_AUTH_SESSION_DATA", authSessionData);
        }
        filterChain.doFilter(request, response);
    }
}