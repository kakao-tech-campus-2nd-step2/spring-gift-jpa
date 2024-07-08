package gift.dto.product;

public record ProductResponse(Long id) {
    public ProductResponse {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }
}
