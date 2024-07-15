package gift.dto;

import gift.entity.Product;
import gift.entity.Wish;
import java.util.Objects;

public final class WishDTO {

    private final Product product;
    private final Integer quantity;

    public WishDTO(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public static WishDTO convertToWishDTO(Wish wish) {
        return new WishDTO(wish.getProduct(), wish.getQuantity());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (WishDTO) obj;
        return Objects.equals(this.product, that.product) &&
                Objects.equals(this.quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }


}