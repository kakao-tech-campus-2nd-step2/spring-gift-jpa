package gift.constant;

public class Message {

    /*SUCCESS*/
    public static final String ADD_SUCCESS_MSG = "상품 추가 성공";
    public static final String UPDATE_SUCCESS_MSG = "상품 수정 성공";
    public static final String DELETE_SUCCESS_MSG = "상품 삭제 성공";

    /*ERROR*/
    public static final String LENGTH_ERROR_MSG = "상품 이름은 공백 포함 15자를 초과할 수 없습니다.";
    public static final String SPECIAL_CHAR_ERROR_MSG = "특수 문자는 (), [], +, -, &, /, _ 외에는 사용할 수 없습니다.";
    public static final String KAKAO_CONTAIN_ERROR_MSG = "상품 이름에 \"카카오\"가 포함된 경우에는 담당 MD와 협의가 필요합니다.";
    public static final String REQUIRED_FIELD_MSG = "필수로 입력해야 하는 항목입니다.";
    public static final String POSITIVE_NUMBER_REQUIRED_MSG = "0이상의 값을 입력해 주세요.";
}
