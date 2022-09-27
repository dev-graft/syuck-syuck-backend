package devgraft.support.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SingleResult<T> extends CommonResult {
    private final T data;

    private SingleResult(final boolean success, final HttpStatus status, final String message, final T data) {
        super(success, status, message);
        this.data = data;
    }

    public static <T> SingleResult<T> success(final T data) {
        return new SingleResult<>(true, HttpStatus.OK, AdviceConstant.SUCCESS, data);
    }

    public static <T> SingleResult<T> error(final String errorMessage, final T data) {
        return new SingleResult<>(false, HttpStatus.BAD_REQUEST, errorMessage, data);
    }

    public static <T> SingleResult<T> error(final HttpStatus status, final String errorMessage, final T data) {
        return new SingleResult<>(false, status, errorMessage, data);
    }
}
