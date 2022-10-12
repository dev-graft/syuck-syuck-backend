package devgraft.support.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ValidationException extends AbstractRequestException {
    private final List<ValidationError> errors;

    public ValidationException(final List<ValidationError> errors, final String message) {
        super(message);
        this.errors = Collections.unmodifiableList(errors);
    }
}
