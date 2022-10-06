package devgraft.support.config.filter;

import devgraft.auth.api.AuthUtils;
import devgraft.auth.query.AuthSessionData;
import devgraft.auth.query.AuthSessionDataDao;
import devgraft.auth.query.AuthSessionDataSpec;
import devgraft.support.jwt.JwtExpiredException;
import devgraft.support.jwt.JwtProvider;
import devgraft.support.jwt.JwtValidationFailedException;
import devgraft.support.jwt.JwtVerifyRequest;
import devgraft.support.jwt.JwtVerifyResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthSessionFilter extends OncePerRequestFilter {
    private final AuthSessionDataDao authSessionDataDao;
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final Optional<AuthUtils.AuthExportData> exportOpt = AuthUtils.export(request);
        if (exportOpt.isPresent()) {
            final AuthUtils.AuthExportData authExportData = exportOpt.get();
            try {
                final JwtVerifyResult jwtVerifyResult = jwtProvider.verify(new JwtVerifyRequest(authExportData.getAccessToken(), authExportData.getRefreshToken()));
                final AuthSessionData authSessionData = authSessionDataDao.findOne(AuthSessionDataSpec.uniqIdEquals(jwtVerifyResult.getUniqId())
                        .and(AuthSessionDataSpec.notBlock())).orElseThrow();

            } catch (JwtExpiredException | JwtValidationFailedException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
