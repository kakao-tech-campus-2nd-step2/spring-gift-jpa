package gift.domain.wish;

import java.util.List;
import java.util.stream.Collectors;

public class WishResponse {
    private Long id;
    private Long productId;
    private int amount;

    public static WishResponse fromModel(Wish wish) {
        WishResponse response = new WishResponse();
        response.setId(wish.getId());
        response.setProductId(wish.getProductId());
        response.setAmount(wish.getAmount());
        return response;
    }
/*
    public static List<WishResponse> fromModelList(List<Wish> wishes) {
        return wishes.stream()
                .map(WishResponse::fromModel)
                .collect(Collectors.toList());
    }

 */

    public static List<WishResponse> fromModelList(List<Wish> wishes) {
        return wishes.stream().map(WishResponse::fromModel).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
