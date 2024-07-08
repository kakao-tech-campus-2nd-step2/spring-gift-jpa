package gift.model;

import java.time.LocalDateTime;

public class Wish {
    private Long id;
    private Long memberId;
    private int productCount;
    private Product product;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Wish(Long id, Long memberId, int productCount, Product product, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.memberId = memberId;
        this.productCount = productCount;
        this.product = product;
        this.createdAt = createdAt;
        this.updatedAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public int getProductCount() {
        return productCount;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
