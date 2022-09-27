package devgraft.support.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SingleResult<T> extends CommonResult {
    private final T data;

    private SingleResult(final boolean success, final String message, final T data) {
        super(success, message);
        this.data = data;
    }

    public static <T> SingleResult<T> success(final T data) {
        return new SingleResult<>(true, AdviceConstant.SUCCESS, data);
    }

    public static <T> SingleResult<T> error(final String errorMessage, final T data) {
        return new SingleResult<>(false, errorMessage, data);
    }
}
