package gift.common;

public class ErrorMessage {
    private ErrorMessage(){}

    public static final String emailAlreadyExists = "이미 가입한 이메일입니다.";
    public static final String emailNotExists = "존재하지 않는 이메일입니다.";
    public static final String passwordInvalid = "잘못된 비밀번호입니다.";
    public static final String tokenInvalid = "유효하지 않은 토큰입니다.";
    public static final String headerInvalid = "유효하지 않은 헤더입니다.";
}
