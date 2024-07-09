package gift.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WishErrorCode {

    PRODUCT_NAME_NOT_EXISTS("존재하지 않는 상품 이름입니다")
    ;
    private String message;

    public String getMessage() {
        return message;
    }
}
