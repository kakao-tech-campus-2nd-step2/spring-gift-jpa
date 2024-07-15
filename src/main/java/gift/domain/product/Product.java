package gift.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    @Column(length = 15)
    private String imgUrl;

    protected Product() {
    }

    public Product(String name, int price, String imgUrl) {
        checkName(name);
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public void update(String name, int price, String imgUrl) {
        checkName(name);
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    private void checkName(String name) {
        if (name.contains("카카오")) {
            throw new IllegalArgumentException("카카오가 포함된 이름은 담당 MD와 협의가 필요합니다.");
        }
    }
}
