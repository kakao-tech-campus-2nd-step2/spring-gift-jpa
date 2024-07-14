package gift.domain.exception;

/**
 * 상품을 찾을 수 없을 때 발생하는 예외
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("The product was not found.");
    }
}
