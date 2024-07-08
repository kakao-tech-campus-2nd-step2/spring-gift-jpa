package gift.exception.CustomException;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class PassWordMissMatchException extends MethodArgumentNotValidException {
    public PassWordMissMatchException(MethodParameter parameter,
        BindingResult bindingResult) {
        super(parameter, bindingResult);
    }
}
