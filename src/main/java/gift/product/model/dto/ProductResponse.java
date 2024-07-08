package gift.product.model.dto;

public record ProductResponse(
        Long id,
        String name,
        int price,
        String imageUrl) {
}