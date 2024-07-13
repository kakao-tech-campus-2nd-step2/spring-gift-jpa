package gift.product.dto;

import gift.product.domain.ImageUrl;
import gift.product.domain.Product;
import gift.product.domain.ProductName;
import gift.product.domain.ProductPrice;

public record ProductServiceDto(Long id, ProductName name, ProductPrice price, ImageUrl imageUrl) {
    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }
}
