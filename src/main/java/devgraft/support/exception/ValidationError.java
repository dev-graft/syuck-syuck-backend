package devgraft.support.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ValidationError {
    private final String field;
    private final String message;

    public static ValidationError of(final String field, final String message) {
        return new ValidationError(field, message);
    }
}
