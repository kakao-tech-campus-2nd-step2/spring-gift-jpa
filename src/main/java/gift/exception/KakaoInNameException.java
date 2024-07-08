package gift.exception;

public class KakaoInNameException extends RuntimeException{

    private static final String MESSAGE = "상품이름에 카카오가 들어가 있습니다. 담당 MD와 협의하세요.";

    public KakaoInNameException(){
        super(MESSAGE);
    }

    public KakaoInNameException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
