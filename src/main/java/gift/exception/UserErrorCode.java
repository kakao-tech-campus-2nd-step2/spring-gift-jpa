package gift.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    WRONG_LOGIN("잘못된 로그인 정보입니다"),
    INVALID_EMAIL_FORMAT("잘못된 이메일 형식입니다"),
//    WEAK_PASSWORD("Password is too weak"),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다"),
    INVALID_TOKEN("유효하지 않거나 만료된 토큰입니다"),
//    CART_NOT_FOUND("Cart not found"),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    ID_NOT_EXISTS("ID가 존재하지 않습니다")
    ;
    private String message;
}
