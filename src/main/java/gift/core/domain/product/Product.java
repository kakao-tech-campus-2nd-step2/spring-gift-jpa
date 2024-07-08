package gift.core.domain.product;

import jakarta.annotation.Nullable;

public record Product(
        Long id,
        String name,
        Integer price,
        String imageUrl
) {
    public Product applyUpdate(
            @Nullable String name,
            @Nullable Integer price,
            @Nullable String imageUrl
    ){
        return new Product(
                this.id(),
                name != null ? name : this.name(),
                price != null ? price : this.price(),
                imageUrl != null ? imageUrl : this.imageUrl()
        );
    }
}
