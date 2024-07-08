package gift.exception;

public class ProductException extends RuntimeException {

    public ProductException(ProductErrorCode productErrorCode) {
        super(productErrorCode.getMessage());
    }

}
