package gift.domain;
public record MenuResponse(
        Long id,
        String name,
        int price,
        String imageUrl
) {

}
