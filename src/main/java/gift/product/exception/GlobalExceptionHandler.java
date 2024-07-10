package gift.product.exception;

import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleDataAccessException(DataAccessException ex, Model model) {
        model.addAttribute("errorMessage", "Database access error: " + ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleSQLException(SQLException ex, Model model) {
        model.addAttribute("errorMessage", "Database error: " + ex.getMessage());
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDuplicateIdException(DuplicateException ex, Model model) {
        model.addAttribute("errorMessage", "Duplicate error: " + ex.getMessage());
    }

    @ExceptionHandler(InstanceValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInstanceValueException(InstanceValueException ex, Model model) {
        model.addAttribute("errorMessage", "Value error: " + ex.getMessage());
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleLoginFailedException(LoginFailedException ex, Model model) {
        model.addAttribute("errorMessage", "Login error: " + ex.getMessage());
    }

}
