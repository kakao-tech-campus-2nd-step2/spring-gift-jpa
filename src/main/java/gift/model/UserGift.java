package gift.model;

public class UserGift {

    private Long userId;
    private Long giftId;
    private int quantity;

    public UserGift() {
    }

    public UserGift(Long userId, Long giftId, int quantity) {
        this.userId = userId;
        this.giftId = giftId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
