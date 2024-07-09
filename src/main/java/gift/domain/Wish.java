package gift.domain;

public class Wish {
    private Long id;
    private Long memberId;
    private String productName;

    // Constructors, getters, and setters
    public Wish() {}

    public Wish(Long memberId, String productName) {
        this.memberId = memberId;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
