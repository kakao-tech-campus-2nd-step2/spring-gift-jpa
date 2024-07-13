package gift.exception;

public class WishException extends RuntimeException {

    private WishErrorCode wishErrorCode;
    private String detailMessage;

    public WishException(WishErrorCode wishErrorCode){
        super(wishErrorCode.getMessage());
        this.wishErrorCode = wishErrorCode;
        this.detailMessage = wishErrorCode.getMessage();
    }

    public WishException(WishErrorCode wishErrorCode, String detailMessage) {
        super(detailMessage);
        this.wishErrorCode = wishErrorCode;
        this.detailMessage = detailMessage;
    }

    public WishErrorCode getWishErrorCode() {
        return wishErrorCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
