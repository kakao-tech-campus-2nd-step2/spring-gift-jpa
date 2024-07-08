package gift.domain;

import gift.dto.WishListDto;

public class WishList {
    
    private long productId;
    private long userId;

    public WishList(long productId, long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public WishListDto toDto(WishList WishList){
        return new WishListDto(this.productId, this.userId);
    }
}
