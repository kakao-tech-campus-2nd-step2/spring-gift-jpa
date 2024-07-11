package gift.main.dto;

import gift.main.annotation.IsValidName;
import gift.main.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductResponce(Long id, String name, int price, String imageUrl, String seller) {

    public ProductResponce(Product product) {
        this(product.getId(),product.getName(), product.getPrice(), product.getImageUrl(), product.getSellerName());
    }
}
