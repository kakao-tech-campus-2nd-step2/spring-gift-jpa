package gift.handler;

import gift.exception.KakaoNameException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KakaoNameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleKakaoNameException(KakaoNameException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(Exception ex, Model model) {
        model.addAttribute("error", "Validation error: " + ex.getMessage());
        return "error/400";
    }
}
