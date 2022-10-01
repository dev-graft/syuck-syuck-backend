package devgraft.support.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommonResult implements Serializable {
    @JsonIgnore
    private HttpStatus status;
    private boolean success;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    protected CommonResult(final boolean success, final HttpStatus status, final String message) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static CommonResult success() {
        return new CommonResult(true, HttpStatus.OK, AdviceConstant.SUCCESS);
    }

    public static CommonResult error(final String errorMessage) {
        return new CommonResult(false, HttpStatus.BAD_REQUEST, errorMessage);
    }

    public static CommonResult error(final HttpStatus status, final String errorMessage) {
        return new CommonResult(false, status, errorMessage);
    }
}
