package devgraft.module.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginDecryptedDataValidator {

    public List<ValidationError> validate(final LoginDecryptedData loginDecryptedData) {
        final List<ValidationError> errors = new ArrayList<>();

        if (!StringUtils.hasText(loginDecryptedData.getLoginId())) {
            errors.add(ValidationError.of("loginId", "LoginRequest.loginId must not be null."));
        }

        if (!StringUtils.hasText(loginDecryptedData.getPassword())) {
            errors.add(ValidationError.of("password", "LoginRequest.password must not be null."));
        }

        return errors;
    }
}
