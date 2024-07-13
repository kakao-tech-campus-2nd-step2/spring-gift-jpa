package gift.model;

public record CreateWishRequest(String email, Long productId, int count) {
}
