package devgraft.support.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class SingleResult<T> extends CommonResult {
    private final T data;

    private SingleResult(final boolean success, final Integer status, final String message, final LocalDateTime timestamp, final T data) {
        super(success, status, message, timestamp);
        this.data = data;
    }

    public static <T>SingleResult<T> success(final T data) {
        return new SingleResult<>(true, HttpStatus.OK.value(), "Success", LocalDateTime.now(), data);
    }

    public static <T>SingleResult<T> success(final T data, final int status) {
        return new SingleResult<>(true, status, "Success", LocalDateTime.now(), data);
    }

    public static <T>SingleResult<T> error(final HttpStatus errorStatus, final String errorMessage, final T data) {
        return new SingleResult<>(false, errorStatus.value(), errorMessage, LocalDateTime.now(), data);
    }

    public static <T>SingleResult<T> error(final int errorStatus, final String errorMessage, final T data) {
        return new SingleResult<>(false, errorStatus, errorMessage, LocalDateTime.now(), data);
    }
}
