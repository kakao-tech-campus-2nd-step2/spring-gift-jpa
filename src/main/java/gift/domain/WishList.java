package gift.domain;

import jakarta.persistence.*;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private Long menuId;

    public WishList() {}

    public WishList(String memberId,Long menuId){
        this(null, memberId,menuId);
    }

    public WishList(Long id, String memberId, Long menuId){
        this.id = id;
        this.memberId = memberId;
        this.menuId = menuId;
    }

    public Long getMenuId(){
        return menuId;
    }

    public Long getId() {
        return id;
    }

    public static WishList MapWishListRequestToWishList(WishListRequest wishListRequest){
        return new WishList(wishListRequest.memberId(),wishListRequest.menuId());
    }

    public static WishListResponse MapWishListToWishListResponse(WishList wishList){
        return new WishListResponse(wishList.id, wishList.menuId);
    }

}
