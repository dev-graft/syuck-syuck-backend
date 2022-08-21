package devgraft.support.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors) {
        this.errors = Collections.unmodifiableList(errors);
    }
}
