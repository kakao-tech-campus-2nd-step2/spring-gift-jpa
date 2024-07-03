package gift.product.dto;

import gift.product.domain.Product;

public record ServiceDto(Long id, String name, int price, String imageUrl) {
    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }
}
