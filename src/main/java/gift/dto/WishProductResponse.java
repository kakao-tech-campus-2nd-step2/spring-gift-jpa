package gift.dto;

public record WishProductResponse(Long id, ProductResponse product, Integer count) {
    public static WishProductResponse of(Long id, ProductResponse product, Integer count) {
        return new WishProductResponse(id, product, count);
    }
}
