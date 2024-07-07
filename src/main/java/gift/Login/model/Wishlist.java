package gift.Login.model;

import java.util.List;
import java.util.UUID;

public class Wishlist {
    private UUID memberId;
    private List<Product> products;

    public Wishlist() {}

    public Wishlist(UUID memberId, List<Product> products) {
        this.memberId = memberId;
        this.products = products;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
