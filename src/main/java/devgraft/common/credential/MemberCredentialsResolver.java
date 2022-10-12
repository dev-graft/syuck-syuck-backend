package devgraft.common.credential;

import devgraft.auth.query.AuthSessionData;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class MemberCredentialsResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(Credentials.class);
        boolean hasAuthResultType = MemberCredentials.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasAuthResultType;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final AuthSessionData authSessionData = (AuthSessionData) httpServletRequest.getAttribute("M_AUTH_SESSION_DATA");

        return MemberCredentials.builder()
                .memberId(authSessionData.getMemberId())
                .version(authSessionData.getVersion())
                .deviceName(authSessionData.getDeviceName())
                .pushToken(authSessionData.getPushToken())
                .os(authSessionData.getOs())
                .build();
    }
}
