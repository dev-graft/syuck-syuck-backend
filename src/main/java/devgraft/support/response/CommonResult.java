package devgraft.support.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommonResult implements Serializable {
    private boolean success;
    private Integer status;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public static CommonResult success() {
        return new CommonResult(true, HttpStatus.OK.value(), "Success", LocalDateTime.now());
    }

    public static CommonResult success(final int status) {
        return new CommonResult(true, status, "Success", LocalDateTime.now());
    }

    public static CommonResult success(final HttpStatus status) {
        return new CommonResult(true, status.value(), "Success", LocalDateTime.now());
    }

    public static CommonResult error(final HttpStatus errorStatus) {
        return new CommonResult(false, errorStatus.value(), errorStatus.getReasonPhrase(), LocalDateTime.now());
    }

    public static CommonResult error(final int errorStatus, final String errorMessage) {
        return new CommonResult(false, errorStatus, errorMessage, LocalDateTime.now());
    }
}
