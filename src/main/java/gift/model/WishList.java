package gift.model;

import jakarta.validation.constraints.NotNull;

public class WishList {

    private final Long id;

    @NotNull
    private final Long productId;

    @NotNull
    private final long userId;

    /**
     * 초기에 클라이언트가 위시리스트에 상품을 추가할 때 사용하는 생성자 id는 null로 설정함.
     *
     * @param productId
     * @param userId
     */
    public WishList(Long productId, long userId) {
        this.id = null;
        this.productId = productId;
        this.userId = userId;
    }

    /**
     * 데이터베이스에서 위시리스트를 조회할 때 사용하는 생성자
     *
     * @param id
     * @param productId
     * @param userId
     */
    public WishList(Long id, Long productId, long userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
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
}
