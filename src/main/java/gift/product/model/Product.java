package gift.product.model;

import gift.wishlist.model.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long product_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String imgUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    // JPA에서 필요로 하는 기본 생성자
    protected Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, int price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // getter 메서드
    public Long product_id() {
        return product_id;
    }

    public String name() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void update(String name, int price, String s) {
    }
}