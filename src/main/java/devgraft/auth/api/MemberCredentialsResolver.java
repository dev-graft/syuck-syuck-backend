package devgraft.auth.api;

import devgraft.auth.query.AuthSessionDataDao;
import devgraft.support.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class MemberCredentialsResolver implements HandlerMethodArgumentResolver {
//    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final AuthSessionDataDao authSessionDataDao;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(MemberCredentials.class);
//        Optional.class.isAssignableFrom(parameter.getParameterType());
//        boolean hasAuthResultType = AuthSessionData.class.isAssignableFrom(parameter.getParameterType());
        return true;
//        return hasAnnotation && hasAuthResultType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final MemberCredentials parameterAnnotation = parameter.getParameterAnnotation(MemberCredentials.class);
//        boolean isRequired = null != parameterAnnotation && parameterAnnotation.required();
        return null;
    }
}
