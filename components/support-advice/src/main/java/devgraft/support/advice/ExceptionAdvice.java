package devgraft.support.advice;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AbstractRequestException.class)
    public Object handleRequestException(final AbstractRequestException e) {
        return CommonResult.error(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public Object handleValidationException(final ValidationException e) {
        return SingleResult.error(e.getStatus(), e.getMessage(), e.getErrors());
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(final RuntimeException e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(final Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        e.printStackTrace();
        final Optional<ObjectError> first = e.getAllErrors().stream().findFirst();
        return CommonResult.error(first.isPresent() ? first.get().getDefaultMessage() : e.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(final AccessDeniedException e) {
        e.printStackTrace();
        return CommonResult.error(e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();
        return CommonResult.error("Unsupported Media Type");
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(final NoHandlerFoundException e) {
        e.printStackTrace();
        return CommonResult.error(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleNotSupportedMethodException(final Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public Object handleServletRequestBindingException(final Exception e) {
        e.printStackTrace();
        return CommonResult.error("Missing Attribute");
    }
}
