package gift.dto;

public record ProductResponse(Long id, String name, Integer price, String imageUrl) {
    public static ProductResponse of(Long id, String name, Integer price, String imageUrl) {
        return new ProductResponse(id, name, price, imageUrl);
    }
}
