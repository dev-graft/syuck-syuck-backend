package devgraft.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class ValidationException extends AbstractRequestException {
    private final List<ValidationError> errors;

    public ValidationException(final List<ValidationError> errors, final String message) {
        super(message, HttpStatus.PAYMENT_REQUIRED);
        this.errors = Collections.unmodifiableList(errors);
    }
}
