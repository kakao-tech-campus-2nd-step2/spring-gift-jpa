package gift.dto;

public record ProductOptionResponse(Long id, ProductBasicInformation productBasicInformation, String name,
                                    Integer additionalPrice) {
    public static ProductOptionResponse of(Long id, ProductBasicInformation productBasicInformation, String name, Integer additionalPrice) {
        return new ProductOptionResponse(id, productBasicInformation, name, additionalPrice);
    }
}
