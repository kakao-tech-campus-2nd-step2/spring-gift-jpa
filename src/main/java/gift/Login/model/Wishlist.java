package gift.Login.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wish")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    // Default constructor
    public Wishlist() {}

    // Constructor with memberId only
    public Wishlist(Long memberId) {
        this.memberId = memberId;
    }

    // Constructor with memberId and products
    public Wishlist(Long memberId, List<Product> products) {
        this.memberId = memberId;
        this.products = products;
    }

    // Getters and Setters
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
