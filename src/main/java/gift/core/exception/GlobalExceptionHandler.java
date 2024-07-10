package gift.core.exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import gift.core.exception.product.DuplicateProductIdException;
import gift.core.exception.product.ProductNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// ProblemDetail 중, 현재 반환 가능한 값만 설정하여 반환한다.
	@ExceptionHandler(ProductNotFoundException.class)
	public ProblemDetail handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setTitle("Product Not Found");
		return problemDetail;
	}

	@ExceptionHandler(DuplicateProductIdException.class)
	public ProblemDetail handleDuplicateProductIdException(DuplicateProductIdException ex, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
		problemDetail.setTitle("Duplicate Product ID");
		return problemDetail;
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
		problemDetail.setTitle("Validation Error");

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		problemDetail.setProperty("errors", errors);

		return problemDetail;
	}
	// TODO: JWTException 핸들러를 추가한다.
}