package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginRequestValidator {
    public List<ValidationError> validate(LoginRequest loginRequest) {
        final ArrayList<ValidationError> errors = new ArrayList<>();
        return errors;
    }
}
