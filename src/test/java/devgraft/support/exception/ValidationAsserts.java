package devgraft.support.exception;

import org.junit.jupiter.api.Assertions;

import java.util.Collection;

public class ValidationAsserts {
    private ValidationAsserts() {}

    /**
     * errors에 field, message가 동일한 요소가 있는지 검색 후 없을 경우 Assertions.fail을 호출합니다.
     * @param errors 에러 리스트
     * @param field 에러 필드 명
     * @param message 에러 메세지
     */
    public static void assertHasCall(final Collection<? extends ValidationError> errors, final String field, final String message) {
        errors.stream()
                .filter(error -> error.equals(field, message))
                .findFirst()
                .ifPresentOrElse(validationError -> {
                }, () -> showAssertMessage(field));

    }

    /**
     * errors에 field, message가 동일한 요소가 있는지 검색 후 있을 경우 Assertions.fail을 호출합니다.
     * @param errors 에러 리스트
     * @param field 에러 필드 명
     * @param message 에러 메세지
     */
    public static void assertHasError(final Collection<? extends ValidationError> errors, final String field, final String message) {
        errors.stream()
                .filter(error -> error.equals(field, message))
                .findFirst()
                .ifPresent(validationError -> showAssertMessage(field));

    }

    public static void assertHasError(final Collection<? extends ValidationError> errors) {
        errors.stream()
                .findFirst()
                .ifPresent(validationError ->
                        Assertions.fail("field: " + validationError.getField() + " message: " + validationError.getMessage()));
    }

    private static void showAssertMessage(final String field) {
        Assertions.fail("[ValidationAssert] ValidationError.field: " + field + " must be call!!");
    }
}