package devgraft.member.app;

import devgraft.support.exception.ValidationError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DecryptedSignUpDataValidator {

    public List<ValidationError> validate(final DecryptedSignUpData a) {
        return new ArrayList<>();
    }
}
