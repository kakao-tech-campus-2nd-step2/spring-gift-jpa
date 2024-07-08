package gift.product.exception;

import gift.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND("P001", HttpStatus.NOT_FOUND, "상품 ID에 해당하는 상품을 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXISTS("P002", HttpStatus.BAD_REQUEST, "생성하려는 상품이 이미 존재합니다."),
    PRODUCT_NOT_ENOUGH_STOCK("P003", HttpStatus.BAD_REQUEST, "상품의 재고가 부족합니다."),
    INVALID_INPUT_VALUE_PRODUCT("P004", HttpStatus.BAD_REQUEST, "상품 생성에 필요한 값이 누락되었거나 잘못되었습니다."),
    PRODUCT_CREATE_FAILED("P005", HttpStatus.INTERNAL_SERVER_ERROR, "상품 생성에 실패했습니다."),
    PRODUCT_UPDATE_FAILED("P006", HttpStatus.INTERNAL_SERVER_ERROR, "상품 정보 수정에 실패했습니다."),
    PRODUCT_DELETE_FAILED("P007", HttpStatus.INTERNAL_SERVER_ERROR, "상품 삭제에 실패했습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ProductErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
