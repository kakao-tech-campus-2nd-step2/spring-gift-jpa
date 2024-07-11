package gift.dto;

public record WishProductResponse(Long id, ProductBasicInformation productBasicInformation, Integer count) {
    public static WishProductResponse of(Long id, ProductBasicInformation productBasicInformation, Integer count) {
        return new WishProductResponse(id, productBasicInformation, count);
    }
}
