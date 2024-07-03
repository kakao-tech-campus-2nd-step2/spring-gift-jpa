package gift.global.response;

import org.springframework.http.HttpStatus;

public enum ResultCode {
    // Product
    GET_ALL_PRODUCTS_SUCCESS(HttpStatus.OK, "P001", "모든 제품 조회 성공"),
    GET_PRODUCT_BY_ID_SUCCESS(HttpStatus.OK, "P002", "단일 제품 조회 성공"),
    CREATE_PRODUCT_SUCCESS(HttpStatus.OK, "P003", "제품 추가 성공"),
    UPDATE_PRODUCT_SUCCESS(HttpStatus.OK, "P002", "제품 수정 성공"),
    DELETE_PRODUCT_SUCCESS(HttpStatus.OK, "P002", "제품 삭제 성공"),

    ;

    // status 를 HttpStatus 로 관리하는 것이 좋을까, 아니면 int로 관리하는 것이 좋을까?
    private final HttpStatus status;
    private final String code;
    private final String message;

    ResultCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
