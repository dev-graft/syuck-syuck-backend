package devgraft.support.exception;

import org.springframework.http.HttpStatus;

public enum StatusConstant {
    BAD_REQUEST("부적절한 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("요청한 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    PAYMENT_REQUIRED("", HttpStatus.PAYMENT_REQUIRED),
    FORBIDDEN("", HttpStatus.FORBIDDEN),
    ALREADY_EXISTS_ERROR("요청 정보가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
            ;


    private final String message;
    private final HttpStatus status;

    StatusConstant(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
