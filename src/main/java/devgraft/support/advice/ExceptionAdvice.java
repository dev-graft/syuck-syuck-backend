package devgraft.support.advice;

import devgraft.support.exception.AbstractRequestException;
import devgraft.support.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    public Object handleRequestException(AbstractRequestException e) {
        return CommonResult.error(e.getHttpStatus().value(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handle(RuntimeException e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        Optional<ObjectError> first = e.getAllErrors().stream().findFirst();
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(),
                first.isPresent() ? first.get().getDefaultMessage() : e.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(), "Unsupported Media Type");
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(NoHandlerFoundException e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleNotSupportedMethodException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingRequestParameterException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public Object handleServletRequestBindingException(Exception e) {
        e.printStackTrace();
        return CommonResult.error(HttpStatus.BAD_REQUEST.value(), "Missing Attribute");
    }
}
