package gift.domain.product;

public record Product(Long id,
                      String name,
                      Long price,
                      String imageUrl) { }
