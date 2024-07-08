package gift.dto;

public class WishResponseDto {
    public final Long id;
    public final Long productId;
    public final String productName;
    public final int productPrice;
    public final String productImageUrl;

    public WishResponseDto(Long id, Long productId, String productName, int productPrice, String productImageUrl) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
    }
}