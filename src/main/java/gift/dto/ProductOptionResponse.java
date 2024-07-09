package gift.dto;

public record ProductOptionResponse(Long id, ProductResponse productResponse, String name, Integer additionalPrice) {
    public static ProductOptionResponse of(Long id, ProductResponse productResponse, String name, Integer additionalPrice) {
        return new ProductOptionResponse(id, productResponse, name, additionalPrice);
    }
}
