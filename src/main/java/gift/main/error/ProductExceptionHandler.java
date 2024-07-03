package gift.main.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String HandleWrongNameException(Model model, IllegalArgumentException e) {
        model.addAttribute("error",e.getMessage());
        String refererUrl = " http://localhost:8080/spring-gift/admin";
        model.addAttribute("refererUrl", refererUrl);
        return "error/error";
    }

}
