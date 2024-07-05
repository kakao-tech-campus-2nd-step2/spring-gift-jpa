package gift.product.dto;

import gift.product.domain.Product;
import gift.product.domain.ProductName;
import gift.product.domain.ProductPrice;

public record ServiceDto(Long id, ProductName name, ProductPrice price, String imageUrl) {
    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }
}
