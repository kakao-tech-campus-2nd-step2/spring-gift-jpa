package gift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductNotFoundException(ProductNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/NotFound";
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleProductAlreadyExistsException(ProductAlreadyExistsException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/AlreadyExists";
    }

    @ExceptionHandler(InvalidProductDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidProductDataException(InvalidProductDataException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/BadRequest";
    }
}
