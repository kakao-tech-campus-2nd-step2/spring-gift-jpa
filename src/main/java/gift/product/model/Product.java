package gift.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9 ()\\[\\]+\\-\\&/\\_]*$", message = "( ), [ ], +, -, &, /, _ 외의 특수문자는 사용이 불가합니다.")
    private String name;

    @Column(nullable = false)
    @Positive(message = "상품 가격은 1 이상의 양수만 입력이 가능합니다.")
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    public Product() {}

    public Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
    public String getImageUrl() {
        return imageUrl;
    }
}
