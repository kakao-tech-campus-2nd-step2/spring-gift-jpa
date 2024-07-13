package gift.web.validation.handler;

import static gift.web.validation.exception.code.ErrorCode.INCORRECT_EMAIL;
import static gift.web.validation.exception.code.ErrorCode.INCORRECT_PASSWORD;
import static gift.web.validation.exception.code.ErrorCode.INTERNAL_SERVER_ERROR;
import static gift.web.validation.exception.code.ErrorCode.INVALID_PARAMETER;
import static gift.web.validation.exception.code.ErrorCode.NOT_FOUND;
import static gift.web.validation.exception.code.ErrorCode.UNAUTHORIZED_INVALID_CREDENTIALS;

import gift.web.dto.response.ErrorResponse;
import gift.web.validation.exception.IncorrectEmailException;
import gift.web.validation.exception.IncorrectPasswordException;
import gift.web.validation.exception.InvalidCredentialsException;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    //todo: handleMethodArgumentNotValidException 메서드 구현
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        BindingResult bindingResult = e.getBindingResult();
//        ErrorResponse errorResponse = ErrorResponse.from(bindingResult);

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ErrorResponse errorResponse = ErrorResponse.from(INVALID_PARAMETER);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ErrorResponse errorResponse = ErrorResponse.from(UNAUTHORIZED_INVALID_CREDENTIALS);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IncorrectEmailException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectEmailException(IncorrectEmailException e) {
        ErrorResponse errorResponse = ErrorResponse.from(INCORRECT_EMAIL);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectPasswordException(IncorrectPasswordException e) {
        ErrorResponse errorResponse = ErrorResponse.from(INCORRECT_PASSWORD);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.from(NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.from(INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
