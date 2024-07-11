package gift.domain.product;

public record Product(
    long id,
    String name,
    long price,
    String imageUrl
    ) {

}
