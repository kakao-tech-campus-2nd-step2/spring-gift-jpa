package gift.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(
        ProductNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(
        MethodArgumentNotValidException e, Model model) {
        Map<String, Object> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        response.put("message", "Validation failed");
        model.addAttribute("errorMessage", response.get("message"));
        return "redirect:/";
    }

    @ExceptionHandler(ForbiddenWordException.class)
    public String handleIllegalArgumentException(
        ForbiddenWordException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(ForbiddenException.class)
    public String handleNotRegisterdAccount(ForbiddenException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/";
    }
}