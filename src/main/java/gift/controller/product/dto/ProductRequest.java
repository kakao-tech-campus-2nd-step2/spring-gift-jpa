package gift.controller.product.dto;


import gift.model.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    public record Register(
        @NotBlank
        String name,
        @Min(0)
        Integer price,
        @NotBlank
        String imageUrl
    ) {

        public Product toEntity() {
            return Product.create(null, name(), price(), imageUrl());
        }
    }

    public record Update(
        @NotBlank
        String name,
        @Min(0)
        Integer price,
        @NotBlank
        String imageUrl
    ) {

    }
}
