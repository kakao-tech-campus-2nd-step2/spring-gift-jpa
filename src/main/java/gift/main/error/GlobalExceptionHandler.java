package gift.main.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String HandleWrongNameException(Model model, Exception e) {
        model.addAttribute("error",e.getMessage());
        String refererUrl = " http://localhost:8080/spring-gift/admin";
        model.addAttribute("refererUrl", refererUrl);
        return "error/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String HandleWrongIdException(Model model, IllegalArgumentException e) {
        model.addAttribute("error",e.getMessage());
        String refererUrl = " http://localhost:8080/spring-gift/admin";
        model.addAttribute("refererUrl", refererUrl);
        return "error/error";
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public String HandleWrongNameException(Model model, ConstraintViolationException e) {
        model.addAttribute("error",e.getMessage());
        String refererUrl = " http://localhost:8080/spring-gift/admin";
        model.addAttribute("refererUrl", refererUrl);
        return "error/error";
    }

}
