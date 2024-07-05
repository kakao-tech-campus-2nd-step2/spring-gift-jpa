package gift.main.global;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e) {
        model.addAttribute("error",e.getMessage());
        String refererUrl = " http://localhost:8080/spring-gift/admin";
        model.addAttribute("refererUrl", refererUrl);
        return "error/error";
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleWrongId(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleWrongProduct(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            if (errorMessageBuilder.length() > 0) {
                errorMessageBuilder.append(", ");
            }
            errorMessageBuilder.append(fieldError.getDefaultMessage());
        }

        String errorMessage = errorMessageBuilder.toString();
        return ResponseEntity.badRequest().body(errorMessage);
    }


}
