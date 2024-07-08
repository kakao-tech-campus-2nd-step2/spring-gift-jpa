package gift.exception.CustomException;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class EmailNotFoundException extends MethodArgumentNotValidException {
    public EmailNotFoundException(MethodParameter parameter,
        BindingResult bindingResult) {
        super(parameter, bindingResult);
    }
}
