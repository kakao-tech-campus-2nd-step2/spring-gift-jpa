package gift.model.wishList;

public class WishItemDto {

    private final Long id;
    private final Long userId;
    private final Long itemId;

    public WishItemDto(Long id, Long userId, Long itemId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemId() {
        return itemId;
    }
}
