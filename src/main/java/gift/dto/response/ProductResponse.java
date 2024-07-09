package gift.dto.response;

public record ProductResponse(
        Long id,
        String name,
        Integer price,
        String imageUrl
) { }
