package gift.util;

import gift.constants.ResponseMsgConstants;
import gift.dto.ResponseDTO;
import gift.exception.BadRequestExceptions.BadRequestException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

    public static ResponseEntity<ResponseDTO> responseError(Exception e) {
        if (e instanceof BadRequestException) {
            return new ResponseEntity<>(new ResponseDTO(true, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

        if (e instanceof EmptyResultDataAccessException) {
            return new ResponseEntity<>(new ResponseDTO(true, "그러한 ID를 가진 상품은 없습니다."),
                    HttpStatus.BAD_REQUEST);
        }

        e.printStackTrace();

        return new ResponseEntity<>(
                new ResponseDTO(true, ResponseMsgConstants.CRITICAL_ERROR_MESSAGE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseDTO> responseError(Exception e, HttpStatus status) {
        return new ResponseEntity<>(new ResponseDTO(true, e.getMessage()),
                status);
    }
}
