package gift.error;

public class WishAlreadyExistsException extends RuntimeException {

    public WishAlreadyExistsException() {
        super("해당 상품이 위시 리스트에 이미 존재합니다.");
    }

}
