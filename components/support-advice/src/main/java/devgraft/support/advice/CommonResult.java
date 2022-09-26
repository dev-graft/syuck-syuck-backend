package devgraft.support.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommonResult implements Serializable {
    private boolean success;
    private Integer status;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    protected CommonResult(final boolean success, final int status, final String message) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static CommonResult success() {
        return new CommonResult(true, HttpStatus.OK.value(), AdviceConstant.SUCCESS);
    }

    public static CommonResult success(final int status) {
        return new CommonResult(true, status, AdviceConstant.SUCCESS);
    }

    public static CommonResult success(final HttpStatus status) {
        return new CommonResult(true, status.value(), AdviceConstant.SUCCESS);
    }

    public static CommonResult error(final HttpStatus errorStatus) {
        return new CommonResult(false, errorStatus.value(), errorStatus.getReasonPhrase());
    }

    public static CommonResult error(final int errorStatus, final String errorMessage) {
        return new CommonResult(false, errorStatus, errorMessage);
    }
}
