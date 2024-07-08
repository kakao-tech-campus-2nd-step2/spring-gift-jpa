package gift.common.exception;

public class ExistWishException extends RuntimeException{

    public ExistWishException() {
        super("이미 위시 리스트에 추가된 상품입니다.");
    }
}
