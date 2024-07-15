package gift.product.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String name;
    private int price;
    private String imgUrl;

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

    public int price() {
        return price;
    }

    public String imgUrl() {
        return imgUrl;
    }

    // 업데이트 메서드
    public void update(String name, int price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}