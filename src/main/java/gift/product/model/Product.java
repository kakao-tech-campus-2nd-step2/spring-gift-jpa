package gift.product.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    private int price;
    public String imgUrl;

    // JPA에서 필요로 하는 기본 생성자
    public Product() {
    }

    public Product(String name, int price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    // 필드 접근 방식으로 값 반환
    public Long id() {
        return id;
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

    public void update(Long id, String name, int price, String s) {

    }
}