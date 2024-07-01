package gift.dto;

import gift.domain.Product;
import gift.exception.ErrorCode;
import gift.exception.InvalidRequestException;

public record CreateProductDto(String name, Integer price, String description, String imageUrl) {
    public Product toProduct() {
        return new Product(name, description, price, imageUrl);
    }

    public CreateProductDto {
        if (name == null || name.isBlank()) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
        if (price == null || price <= 0) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
        if (description == null || description.isBlank()) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }
}
