package gift.ssr;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        return "exception-page";
    }
}
