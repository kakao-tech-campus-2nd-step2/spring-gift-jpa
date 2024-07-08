package gift.model;

public class WishList {

    private long id;
    private String email;
    private long memberId;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    private String productName;
    private long quantity;

    public WishList() {
    }

    public WishList(long id, String email, long memberId, String productName, long quantity) {
        this.id = id;
        this.email = email;
        this.memberId = memberId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
