package gift.entity;

import gift.exception.BusinessException;
import gift.exception.ErrorCode;

public class Product {
    public final Long id;
    public final ProductName name;
    public final int price;
    public final String imageUrl;

    public Product(Long id, ProductName name, int price, String imageUrl) {
        validatePrice(price);
        validateImageUrl(imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(ProductName name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Product update(ProductName name, int price, String imageUrl) {
        validatePrice(price);
        validateImageUrl(imageUrl);
        return new Product(this.id, name, price, imageUrl);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new BusinessException(ErrorCode.INVALID_PRICE);
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_URL);
        }
    }
}
