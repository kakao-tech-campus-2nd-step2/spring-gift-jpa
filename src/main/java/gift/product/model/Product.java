package gift.product.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Product {

    @NotNull(message = "ID 속성이 누락되었습니다.")
    private Long id;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9 ()\\[\\]+\\-\\&/\\_]*$", message = "( ), [ ], +, -, &, /, _ 외의 특수문자는 사용이 불가합니다.")
    @NotBlank(message = "상품명은 필수 입력 요소입니다.")
    @Size(max = 15, message = "입력 가능한 상품명은 공백 포함 최대 15자 입니다.")
    private String name;

    @Positive(message = "상품 가격은 1 이상의 양수만 입력이 가능합니다.")
    private int price;

    @NotBlank(message = "상품 이미지 URL은 필수 입력 요소입니다.")
    private String imageUrl;

    public Product(long id, String name, int price, String imageUrl) {
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
