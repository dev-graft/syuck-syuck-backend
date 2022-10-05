package devgraft.auth.api;

import devgraft.auth.query.AuthSessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthSessionInfoResolver implements HandlerMethodArgumentResolver {
//    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(AuthSessionInfo.class);
//        Optional.class.isAssignableFrom(parameter.getParameterType());
        boolean hasAuthResultType = AuthSessionData.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasAuthResultType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Optional<AuthUtils.AuthExportData> exportOpt = AuthUtils.export((HttpServletRequest) webRequest.getNativeRequest());
//        if (exportOpt.isEmpty() )
        // 인가정보 갖고 옴
            // 없으면 필수인지 검증 후 예외처리
        // 있으면 인가정보 검증 (jwt)
            // 없으면 필수인지 검증 후 예외처리
        // jwt의 uniq 아이디 기반 authSession query 조회
        // 존재 여부, block 여부 검증
            // 실패 시 필수 검증 후 예외처리
        // 인가정보 반환
        return null;
//        Optional<AuthResult> authResultOpt = authService.exportAuthorization((HttpServletRequest) webRequest.getNativeRequest());
//        return authResultOpt.orElseThrow(AuthInfoNotFoundException::new);
    }
}
