package devgraft.support.exception;

import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ValidationAsserts {
    public static void assertHasCall(List<ValidationError> errors, String field, String message) {
        errors.stream().filter(error ->
                        error.getField().equals(field) && error.getMessage().equals(message))
                .findFirst()
                .ifPresentOrElse(validationError -> {},
                        ()-> Assertions.fail("[ValidationAssert] ValidationError.field: " + field + " must be call!!"));

    }

    public static void assertHasError(List<ValidationError> errors, String field, String message) {
        errors.stream().filter(error ->
                        error.getField().equals(field) && error.getMessage().equals(message))
                .findFirst()
                .ifPresent(validationError -> Assertions.fail("[ValidationAssert] ValidationError.field: " + field + " must be call!!"));

    }

    public static void assertHasError(List<ValidationError> errors) {
        errors.stream().findFirst().ifPresent(validationError -> {
            Assertions.fail("field: " + validationError.getField() + " message: " + validationError.getMessage());
        });
    }
}
