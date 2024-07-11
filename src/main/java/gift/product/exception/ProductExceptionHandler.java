package gift.product.exception;

import java.util.NoSuchElementException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// product package 예외 핸들러.
@ControllerAdvice(basePackages = "gift.product")
public class ProductExceptionHandler {

    private final String ERROR_PAGE = "html/error-product";

    // IllegalArgument 핸들러 함수
    @ExceptionHandler(IllegalArgumentException.class)
    public String handler(IllegalArgumentException illegalArgumentException, Model model) {
        // 에러 메시지 받아옴
        String message = illegalArgumentException.getMessage();

        // 반환
        addAttributesForManagerPage(message, model);
        return ERROR_PAGE;
    }

    // NoSuchElement 핸들러 함수
    @ExceptionHandler(NoSuchElementException.class)
    public String handler(NoSuchElementException noSuchElementException, Model model) {
        // 에러 메시지 받아옴
        String message = noSuchElementException.getMessage();

        // 반환
        addAttributesForManagerPage(message, model);
        return ERROR_PAGE;
    }

    // MethodArgumentNotValid 핸들러 함수
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handler(MethodArgumentNotValidException methodArgumentNotValidException,
        Model model) {
        // 에러 메시지 받아옴
        String message = methodArgumentNotValidException.getFieldError().getDefaultMessage();

        // 반환
        addAttributesForManagerPage(message, model);
        return ERROR_PAGE;
    }

    // error-product.html에서 보여줄 attributes를 넣는 함수
    private void addAttributesForManagerPage(String message, Model model) {
        // 에러 메시지 넣어줌
        model.addAttribute("message", message);
    }
}
