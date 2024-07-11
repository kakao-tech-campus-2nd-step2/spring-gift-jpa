package gift.DTO.Wish;

import gift.DTO.Product.ProductResponse;
import gift.DTO.User.UserResponse;

public class WishProductResponse {
    Long id;
    UserResponse user;
    ProductResponse product;
    int count;

    public WishProductResponse(Long id, UserResponse user, ProductResponse product, int count) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.count = count;
    }

    public Long getId(){
        return id;
    }
    public UserResponse getUser() {
        return user;
    }

    public ProductResponse getProduct() {
        return product;
    }
    public int getCount(){
        return count;
    }
}
