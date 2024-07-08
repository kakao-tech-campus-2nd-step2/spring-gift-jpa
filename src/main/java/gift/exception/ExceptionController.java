package gift.exception;

import gift.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> invalidRequestHandler(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult("400", "잘못된 요청입니다.");

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResult.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResult> productNotFoundHandler(EntityNotFoundException e){
        ErrorResult errorResult = new ErrorResult("400", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(KakaoInNameException.class)
    public ResponseEntity<ErrorResult> kakaoInNameHandler(KakaoInNameException e){
        ErrorResult errorResult = new ErrorResult("400", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> errorHandler(Exception e) {
        ErrorResult errorResult = new ErrorResult("500", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResult> KeyNotFoundExceptionHandler(MemberNotFoundException e) {
        ErrorResult errorResult = new ErrorResult("403", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @ExceptionHandler(EmailDuplicationException.class)
    public ResponseEntity<ErrorResult> EmailDuplicationHandler(EmailDuplicationException e){
        ErrorResult errorResult = new ErrorResult("400", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<ErrorResult> KeyNotFoundExceptionHandler(UnAuthorizationException e) {
        ErrorResult errorResult = new ErrorResult("401", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResult> KeyNotFoundExceptionHandler(ForbiddenException e) {
        ErrorResult errorResult = new ErrorResult("403", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.FORBIDDEN);
    }

}
