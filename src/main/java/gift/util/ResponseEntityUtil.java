package gift.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import gift.constants.ResponseMsgConstants;
import gift.dto.ResponseDTO;
import gift.exception.BadRequestExceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

    public static ResponseEntity<ResponseDTO> responseError(Exception e) {
        if (e instanceof BadRequestException) {
            return new ResponseEntity<>(new ResponseDTO(true, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        if (e instanceof JsonProcessingException) {
            return new ResponseEntity<>(new ResponseDTO(true, ResponseMsgConstants.CRITICAL_ERROR_MESSAGE),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        e.printStackTrace();
        return new ResponseEntity<>(
                new ResponseDTO(true, ResponseMsgConstants.CRITICAL_ERROR_MESSAGE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseDTO> responseError(RuntimeException e, HttpStatus status) {
        return new ResponseEntity<>(new ResponseDTO(true, e.getMessage()),
                status);
    }
}
