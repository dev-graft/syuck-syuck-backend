package devgraft.support.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SingleResult<T> extends CommonResult {
    private final T data;

    private SingleResult(final boolean success, final Integer status, final String message, final T data) {
        super(success, status, message);
        this.data = data;
    }

    public static <T> SingleResult<T> success(final T data) {
        return new SingleResult<>(true, HttpStatus.OK.value(), AdviceConstant.SUCCESS, data);
    }

    public static <T> SingleResult<T> success(final T data, final int status) {
        return new SingleResult<>(true, status, AdviceConstant.SUCCESS, data);
    }

    public static <T> SingleResult<T> error(final HttpStatus errorStatus, final String errorMessage, final T data) {
        return new SingleResult<>(false, errorStatus.value(), errorMessage, data);
    }

    public static <T> SingleResult<T> error(final int errorStatus, final String errorMessage, final T data) {
        return new SingleResult<>(false, errorStatus, errorMessage, data);
    }
}
