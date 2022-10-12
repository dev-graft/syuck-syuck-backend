package devgraft.auth.api;

import devgraft.auth.domain.AuthorizationElements;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


@Component
public class AuthCodeIOUtils {
    private static final String HEADER_ACCESS_TOKEN_SYNTAX = "ACCESS-TOKEN";
    private static final String COOKIE_REFRESH_TOKEN_SYNTAX = "REFRESH-TOKEN";
    private static final Cookie[] COOKIES = {};

    public void injectAuthorizationCode(final HttpServletResponse response, AuthorizationElements authorizationElements) {
        response.addHeader(HEADER_ACCESS_TOKEN_SYNTAX, authorizationElements.getAccessToken());
        Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN_SYNTAX, authorizationElements.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    public Optional<AuthorizationCode> exportAuthorizationCode(final HttpServletRequest request) {
        final String accessToken = request.getHeader(HEADER_ACCESS_TOKEN_SYNTAX);

        Optional<Cookie> cookieOpt = Arrays.stream(null != request.getCookies() ? request.getCookies() : COOKIES)
                .filter(cookie -> Objects.equals(cookie.getName(), COOKIE_REFRESH_TOKEN_SYNTAX))
                .findFirst();

        if (cookieOpt.isEmpty() || !StringUtils.hasText(accessToken)){
            return Optional.empty();
        }

        String refreshToken = cookieOpt.get().getValue();

        return Optional.of(new AuthorizationCode(accessToken, refreshToken));
    }

    @Getter
    public static class AuthorizationCode implements AuthorizationElements {
        private final String accessToken;
        private final String refreshToken;

        public AuthorizationCode(final String accessToken, final String refreshToken) {
            Assert.notNull(accessToken, "AuthorizationToken.accessToken must not be null");
            Assert.notNull(refreshToken, "AuthorizationToken.refreshToken must not be null");
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public static AuthorizationCode of(final String accessToken, final String refreshToken) {
            return new AuthorizationCode(accessToken, refreshToken);
        }
    }
}
