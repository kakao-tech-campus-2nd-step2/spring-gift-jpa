package gift.model;

public class Wish {
    private Long id;
    private Long productId;
    private Long userId;
    private Integer amount;
    private Boolean isDeleted;

    public Wish(Long id, Long productId, Long userId, Integer amount) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.isDeleted = false;
    }

    public Wish(Long productId, Long userId, Integer amount) {
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.isDeleted = false;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public boolean isNew() {
        return id == null;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
