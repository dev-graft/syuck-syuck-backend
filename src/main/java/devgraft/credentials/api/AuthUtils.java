package devgraft.credentials.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static devgraft.common.StrConstant.HEADER_TOKEN_PREFIX;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {
    private static final String HEADER_ACCESS_TOKEN_SYNTAX = "ACCESS-TOKEN";
    private static final String COOKIE_REFRESH_TOKEN_SYNTAX = "REFRESH-TOKEN";

    /**
     * 헤더, 쿠키에 인가 정보 주입
     * @param response
     * @param accessToken
     * @param refreshToken
     */
    public static void inject(final HttpServletResponse response, final String accessToken, final String refreshToken) {
        response.addHeader(HEADER_ACCESS_TOKEN_SYNTAX, accessToken);
        Cookie cookie = new Cookie(COOKIE_REFRESH_TOKEN_SYNTAX, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 헤더 & 쿠키에서 인가 정보 꺼내기
     * @param request
     * @return
     */
    public static Optional<AuthExportData> export(final HttpServletRequest request) {
        final String accessToken = request.getHeader(HEADER_ACCESS_TOKEN_SYNTAX);

        Optional<Cookie> cookieOpt = Arrays.stream(null != request.getCookies() ? request.getCookies() : new Cookie[]{})
                .filter(cookie -> Objects.equals(cookie.getName(), COOKIE_REFRESH_TOKEN_SYNTAX))
                .findFirst();

        if (!StringUtils.hasText(accessToken) || !accessToken.startsWith(HEADER_TOKEN_PREFIX) || cookieOpt.isEmpty()) {
            return Optional.empty();
        }

        final String _accessToken = accessToken.replaceAll(HEADER_TOKEN_PREFIX, "");
        String refreshToken = cookieOpt.get().getValue();

        return Optional.of(new AuthExportData(_accessToken, refreshToken));
    }



    @AllArgsConstructor
    @Getter
    public static class AuthExportData {
        private final String accessToken;
        private final String refreshToken;
    }
}
