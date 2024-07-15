package gift.wishlist.model;

import gift.member.model.Member;
import gift.product.model.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class WishList {

    @Id
    private String wishlist_id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    // 기본 생성자 (JPA에서 필요로 함)
    protected WishList() {
        this.wishlist_id = UUID.randomUUID().toString();
    }

    // 생성자
    public WishList(Member member, Product product) {
        this();
        this.member = member;
        this.products.add(product);
    }

    public WishList(Member member, List<Product> products) {
        this();
        this.member = member;
        this.products.addAll(products);
    }

    // getter 메서드
    public String wishlist_id() {
        return wishlist_id;
    }

    public Member member() {
        return member;
    }

    public List<Product> products() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}