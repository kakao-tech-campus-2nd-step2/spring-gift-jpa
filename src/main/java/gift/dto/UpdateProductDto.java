package gift.dto;

import gift.domain.Product;

public record UpdateProductDto(Long id, String name, Integer price, String description, String imageUrl) {
    public Product toProduct() {
        return new Product(name, description, price, imageUrl);
    }
}
