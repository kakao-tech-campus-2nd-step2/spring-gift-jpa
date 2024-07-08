package gift.dto;

public record ProductOptionResponse(Long id, Long productId, String name, Integer additionalPrice) {
    public static ProductOptionResponse of(Long id, Long productId, String name, Integer additionalPrice) {
        return new ProductOptionResponse(id, productId, name, additionalPrice);
    }
}
