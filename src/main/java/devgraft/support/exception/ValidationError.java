package devgraft.support.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ValidationError {
    private final String field;
    private final String message;

    public static ValidationError of(final String field, final String message) {
        return new ValidationError(field, message);
    }

    public boolean equals(final String cField, final String cMessage) {
        return Objects.equals(field, cField) && Objects.equals(message, cMessage);
    }
}
