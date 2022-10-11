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
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthCodeFilter extends OncePerRequestFilter {
    private final List<PathPattern> whitelist = Arrays.asList(
            new PathPatternParser().parse("/api/v1/auth/refresh"),
            new PathPatternParser().parse("/api/v1/auth/refresh"),
            new PathPatternParser().parse("/v*/api-docs"),
            new PathPatternParser().parse("/docs/**"),
            new PathPatternParser().parse("/swagger-resources/**"),
            new PathPatternParser().parse("/swagger-ui.html"),
            new PathPatternParser().parse("/webjars/**"),
            new PathPatternParser().parse("/swagger/**"));
    private final AuthCodeVerificationService authCodeVerificationService;
    private final AuthSessionDataDao authSessionDataDao;
    // TODO Filter에서 발생하는 에러는 ExceptionHandler가 잡지 못하니 수정 필요함. (Advice는 결과 캐치함)
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final Optional<AuthorizationCode> authorizationCodeOpt = AuthCodeIOService.exportAuthorizationCode(request);
        final boolean isMatch = whitelist.stream().noneMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(request.getRequestURI())));
        if (isMatch && authorizationCodeOpt.isPresent()) {
            final AuthorizationCode authorizationCode = authorizationCodeOpt.get();
            final String uniqId = authCodeVerificationService.verify(authorizationCode);
            final AuthSessionData authSessionData = authSessionDataDao.findOne(AuthSessionDataSpec.uniqIdEquals(uniqId)).orElseThrow(NotFoundAuthSessionException::new);
            if (authSessionData.isBlock()) throw new BlockAuthSessionException();

            request.setAttribute("M_AUTH_SESSION_DATA", authSessionData);
        }
        filterChain.doFilter(request, response);
    }
}