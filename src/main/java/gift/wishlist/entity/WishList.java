package gift.wishlist.entity;

import static jakarta.persistence.GenerationType.*;

import gift.wishlist.dto.WishListReqDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long memberId;
    private Long productId;
    private Integer quantity;

    public WishList(Long memberId, Long productId, Integer quantity) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    protected WishList() {
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void update(WishListReqDto wishListReqDto) {
        this.productId = wishListReqDto.productId();
        this.quantity = wishListReqDto.quantity();
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
