package gift.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@NotNull(message = "ID 속성이 누락되었습니다.")
    private Long id;

    @Column(nullable = false, length = 15)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9 ()\\[\\]+\\-\\&/\\_]*$", message = "( ), [ ], +, -, &, /, _ 외의 특수문자는 사용이 불가합니다.")
    @NotBlank(message = "상품명은 필수 입력 요소입니다.")
    @Size(max = 15, message = "입력 가능한 상품명은 공백 포함 최대 15자 입니다.")
    private String name;

    @Column(nullable = false)
    @Positive(message = "상품 가격은 1 이상의 양수만 입력이 가능합니다.")
    private int price;

    @Column(nullable = false)
    @NotBlank(message = "상품 이미지 URL은 필수 입력 요소입니다.")
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
