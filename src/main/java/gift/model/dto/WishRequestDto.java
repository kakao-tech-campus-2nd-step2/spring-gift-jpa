package gift.model.dto;

import gift.model.Wish;

public class WishRequestDto {

    private String productName;
    private Integer count;

    public WishRequestDto() {
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Wish toEntity() {
        return new Wish(
            productName,
            count
        );
    }
}
