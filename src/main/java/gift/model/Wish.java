package gift.model;

public class Wish {

    private Long memberId;
    private String productName;
    private Integer count;

    public Wish(Long memberId, String productName, Integer count) {
        this.memberId = memberId;
        this.productName = productName;
        this.count = count;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getCount() {
        return count;
    }
}
