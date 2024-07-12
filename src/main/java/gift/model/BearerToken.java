package gift.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

public class BearerToken {
    private final String token;

    public BearerToken(String token) throws AuthenticationException {
        this.token = parseToken(token);
    }

    public String parseToken(String token) throws AuthenticationException{
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        else{
            throw new AuthenticationException("헤더 혹은 토큰이 유효하지 않습니다.");
        }
    }

    public String getToken() {
        return token;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e) {
        return e.getMessage();
    }
}
