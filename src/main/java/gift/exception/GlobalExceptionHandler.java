package gift.exception;

import gift.dto.UserLogin.BadResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ModelAndView handleProductException(ProductException e, Model model){
        model.addAttribute("errorCode", e.getProductErrorCode());
        model.addAttribute("errorMessage", e.getDetailMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public BadResponse handleForbiddenLoginException(UserException.Forbidden e){
        return new BadResponse(e.getDetailMessage());
    }

    @ExceptionHandler(UserException.BadToken.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadResponse handleBadTokenException(UserException.Forbidden e){
        return new BadResponse(e.getDetailMessage());
    }
    @ExceptionHandler(UserException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadResponse handleBadRequestException(UserException.BadRequest e){
        return new BadResponse(e.getDetailMessage());
    }
    @ExceptionHandler(WishException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadResponse handleBadWishRequestException(WishException e){
        return new BadResponse(e.getDetailMessage());
    }
}
