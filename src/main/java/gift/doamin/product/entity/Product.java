package gift.doamin.product.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class Product {

    private Long id;

    private Long userId;

    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9 ()\\[\\]+\\-&/_]{1,15}$", message = "영문, 한글, 숫자, 공백, 특수문자 ()[]+-&/_ 1자 이상 15자 미만으로 입력해야 합니다.")
    private String name;

    @NotNull
    @PositiveOrZero
    private Integer price;
    private String imageUrl;

    public Product(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
