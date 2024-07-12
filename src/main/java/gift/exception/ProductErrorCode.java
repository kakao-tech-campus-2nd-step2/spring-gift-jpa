package gift.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {
    INVALID_PRODUCT_LENGTH("상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다"),
    CONTAINS_FORBIDDEN_SPECIAL_CHAR("( ), [ ], +, -, &, /, _ 특수문자만 사용 가능합니다"),
    CONTAINS_KAKAO("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다"),
    ID_NOT_EXISTS("해당하는 상품이 존재하지 않습니다"),

    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.")
    ;

    private final String message;
}
