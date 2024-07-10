package gift.common.exception;

public class WishNotFoundException extends RuntimeException{

    public WishNotFoundException() {
        super("위시리스트에 존재하지 않는 상품입니다.");
    }
}
