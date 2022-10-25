package devgraft.common.credential;

import com.fasterxml.jackson.core.type.TypeReference;
import devgraft.auth.query.AuthSessionData;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Component
public class OptionalMemberCredentialsResolver implements HandlerMethodArgumentResolver {
    private static final String OPTIONAL_MEMBER_CREDENTIALS_TYPE = new OptionalMemberCredentialsResolver.OptionalTypeReference().getType().getTypeName();
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(Credentials.class);
        boolean hasOptional = Objects.equals(OPTIONAL_MEMBER_CREDENTIALS_TYPE, parameter.getGenericParameterType().getTypeName());
        return hasAnnotation && hasOptional;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final AuthSessionData authSessionData = (AuthSessionData) httpServletRequest.getAttribute("M_AUTH_SESSION_DATA");
        if (null == authSessionData) return Optional.empty();
        return Optional.of(MemberCredentials.builder()
                .memberId(authSessionData.getMemberId())
                .version(authSessionData.getVersion())
                .deviceName(authSessionData.getDeviceName())
                .pushToken(authSessionData.getPushToken())
                .os(authSessionData.getOs())
                .build());
    }

    private static class OptionalTypeReference extends TypeReference<Optional<MemberCredentials>> {
    }
}
