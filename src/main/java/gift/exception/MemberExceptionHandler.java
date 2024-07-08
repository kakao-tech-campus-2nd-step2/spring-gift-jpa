package gift.exception;

import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MemberException.class)
    public ErrorResult joinExHandle(MemberException e) {
        return new ErrorResult("회원가입 에러", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({LoginErrorException.class})
    public ErrorResult notFoundUserExHandle(LoginErrorException e) {
        return new ErrorResult("로그인 에러", e.getMessage());
    }

}
