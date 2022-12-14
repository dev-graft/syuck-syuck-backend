package devgraft.auth.api;

import devgraft.auth.api.AuthCodeIOUtils.AuthorizationCode;
import devgraft.auth.app.SignInRefreshService;
import devgraft.auth.app.SignInRefreshService.SignInRefreshRequest;
import devgraft.auth.app.SignInRefreshService.SignInRefreshResult;
import devgraft.common.JsonLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static devgraft.common.URLPrefix.API_PREFIX;
import static devgraft.common.URLPrefix.AUTH_URL_PREFIX;
import static devgraft.common.URLPrefix.VERSION_1_PREFIX;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignInRefreshApi {
    private final AuthCodeIOUtils authCodeIOUtils;
    private final SignInRefreshService signInRefreshService;

    @GetMapping(API_PREFIX + VERSION_1_PREFIX + AUTH_URL_PREFIX + "/refresh")
    public String refresh(final HttpServletRequest request, final HttpServletResponse response) {
        JsonLogger.logI(log, "SignInRefreshApi.refresh 요청");
        final AuthorizationCode authorizationCode = authCodeIOUtils.exportAuthorizationCode(request).orElseThrow(NotIssuedAuthCodeException::new);
        final SignInRefreshResult refreshResult = signInRefreshService.refresh(new SignInRefreshRequest(authorizationCode.getAccessToken(), authorizationCode.getRefreshToken()));
        authCodeIOUtils.injectAuthorizationCode(response, refreshResult);
        return "Success";
    }
}
