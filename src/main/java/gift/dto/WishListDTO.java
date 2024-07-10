package gift.dto;

import gift.model.Wish;
import java.util.HashMap;
import java.util.Map;

public class WishListDTO {

    private Long memberId;
    private Map<String,Integer> wishList;


    public WishListDTO() {
    }

    public WishListDTO(Wish wish){
        this.memberId = wish.getMember().getId();
        this.wishList = new HashMap<>();
    }

    public WishListDTO(Long memberId, Map<String, Integer> wishList) {
        this.memberId = memberId;
        this.wishList = wishList;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void addProduct(String, Integer value) {
        this.wishList.put(productId.toString(), value);
    }

    public Map<String, Integer> getWishList() {
        return wishList;
    }

}
