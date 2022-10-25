package devgraft.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import devgraft.auth.api.AuthCodeIOUtils.AuthorizationCode;
import devgraft.auth.app.AuthCodeVerificationService;
import devgraft.auth.app.BlockAuthSessionException;
import devgraft.auth.app.NotFoundAuthSessionException;
import devgraft.auth.query.AuthSessionData;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.auth.query.AuthSessionDataSpec;
import devgraft.member.query.MemberData;
import devgraft.member.query.MemberDataDao;
import devgraft.member.query.MemberDataSpec;
import devgraft.support.advice.CommonResult;
import devgraft.support.exception.AbstractRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final ObjectMapper objectMapper;
    private final AuthCodeVerificationService authCodeVerificationService;
    private final AuthSessionDataDao authSessionDataDao;
    private final MemberDataDao memberDataDao;
    private final AuthCodeIOUtils authCodeIOUtils;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final Optional<AuthorizationCode> authorizationCodeOpt = authCodeIOUtils.exportAuthorizationCode(request);
        final boolean isMatch = whitelist.stream().noneMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(request.getRequestURI())));
        if (isMatch && authorizationCodeOpt.isPresent()) {
            final AuthorizationCode authorizationCode = authorizationCodeOpt.get();
            try {
                final String uniqId = authCodeVerificationService.verify(authorizationCode);
                final AuthSessionData authSessionData = authSessionDataDao.findOne(AuthSessionDataSpec.uniqIdEquals(uniqId)).orElseThrow(NotFoundAuthSessionException::new);
                if (authSessionData.isBlock()) throw new BlockAuthSessionException();

                final MemberData memberData = memberDataDao.findOne(MemberDataSpec.loggedIdEquals(authSessionData.getMemberId())).orElseThrow(NotFoundAuthSessionException::new);
                if (memberData.isLeave()) throw new BlockAuthSessionException();

                request.setAttribute("M_AUTH_SESSION_DATA", authSessionData);
                request.setAttribute("M_MEMBER_DATA", memberData);
            } catch (AbstractRequestException e) {
                setErrorResponse(e, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(AbstractRequestException e, final HttpServletResponse response) {
        try {
            response.setStatus(e.getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(CommonResult.error(e.getStatus(), e.getMessage())));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class FilterException extends AbstractRequestException {
        public FilterException(final String message, final HttpStatus status) {
            super(message, status);
        }
    }
}