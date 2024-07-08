package gift.model.dto;

import gift.model.Wish;

public class WishResponseDto {

    private String productName;
    private Integer count;

    public WishResponseDto() {
    }

    public WishResponseDto(String productName, Integer count) {
        this.productName = productName;
        this.count = count;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getCount() {
        return count;
    }

    public static WishResponseDto from(Wish wish) {
        return new WishResponseDto(
            wish.getProductName(),
            wish.getCount()
        );
    }
}
