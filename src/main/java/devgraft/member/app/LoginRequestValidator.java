package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginRequestValidator {
    public List<ValidationError> validate(LoginRequest request) {
        final List<ValidationError> errors = new ArrayList<>();

        if (!StringUtils.hasText(request.getLoginId())) {
            errors.add(ValidationError.of("loginId", "LoginRequest.loginId must not be null."));
        }

        if (!StringUtils.hasText(request.getPassword())) {
            errors.add(ValidationError.of("password", "LoginRequest.password must not be null."));
        }
        return errors;
    }
}
