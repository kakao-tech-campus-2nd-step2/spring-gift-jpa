package gift.request;

import jakarta.validation.constraints.NotNull;

public class WishCreateRequest {

    @NotNull
    private Long productId;

    public WishCreateRequest() {
    }

    // TODO: 아래처럼 테스트를 위해 생성자 같은 추가 코드를 작성해도 되는지. 뭔가 테스트를 위해 코드를 추가하는게 찝찝
    public WishCreateRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
