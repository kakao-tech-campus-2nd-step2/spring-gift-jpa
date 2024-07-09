package gift.domain;

public class WishList {
    private Long id;
    private String memberId;
    private Long menuId;

    public WishList(Long id, String memberId, Long menuId){
        this.id = id;
        this.memberId = memberId;
        this.menuId = menuId;
    }
}
