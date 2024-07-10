package gift.dto;

import gift.domain.Product;
import gift.domain.Wish;

public class WishResponseDto {
    private Long id;
    private Product product;
    private int quantity;

    public WishResponseDto(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static WishResponseDto from(final Wish wish){
        return new WishResponseDto(wish.getId(), wish.getProduct(), wish.getQuantity());
    }
}
