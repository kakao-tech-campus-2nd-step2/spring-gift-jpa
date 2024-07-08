package gift.entity;

public class Wish {
    private String email;
    private String type;
    private long productId;

    public Wish(String email, String type, long productId) {
        this.email = email;
        this.type = type;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


}

