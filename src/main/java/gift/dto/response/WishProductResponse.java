package gift.dto.response;

public record WishProductResponse (
        Long productId,
        String productName,
        int productPrice,
        String productImageUrl,
        int productAmount
) { }
