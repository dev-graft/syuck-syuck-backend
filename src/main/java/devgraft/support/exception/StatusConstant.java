package devgraft.support.exception;

import org.springframework.http.HttpStatus;

public enum StatusConstant {
    PAYMENT_REQUIRED("", HttpStatus.PAYMENT_REQUIRED),
    // 4oo,
    BAD_REQUEST("부적절한 요청입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_ERROR("요청 정보가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
    UNRELATED_REQUEST_ERROR("자신과 관계 없는 요청은 처리할 수 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED("인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND("요청한 정보를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("제공하지 않는 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
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
