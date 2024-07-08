package gift.product.model;

import gift.Login.validation.NoKaKao;
import jakarta.validation.constraints.*;
import java.util.Objects;

public class Product {
    private Long id;

    @NotNull(message = "커피 이름은 필수 입력 값입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9 \\(\\)\\[\\]\\+\\-\\&\\/\\_]*$|^[a-zA-Z0-9가-힣 ]*$",
            message = "상품 이름에는 특수 문자는 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다."
    )
    @NoKaKao
    private String name;

    @NotNull(message = "커피 가격은 필수 입력 값입니다.")
    @Min(value = 0, message = "가격은 0 이상의 정수만 입력할 수 있습니다.")
    private Long price;

    @NotNull(message = "온도 옵션은 필수 입력 값입니다.")
    private String temperatureOption;

    @NotNull(message = "컵 옵션은 필수 입력 값입니다.")
    private String cupOption;

    @NotNull(message = "사이즈 옵션은 필수 입력 값입니다.")
    private String sizeOption;

    @NotNull(message = "이미지 URL은 필수 입력 값입니다.")
    @Size(max = 255, message = "이미지 URL은 최대 255자까지 입력할 수 있습니다.")
    private String imageurl;

    public boolean equalProduct(Product product) {
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(temperatureOption, product.temperatureOption) &&
                Objects.equals(cupOption, product.cupOption) &&
                Objects.equals(sizeOption, product.sizeOption) &&
                Objects.equals(imageurl, product.imageurl);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTemperatureOption() {
        return temperatureOption;
    }

    public void setTemperatureOption(String temperatureOption) {
        this.temperatureOption = temperatureOption;
    }

    public String getCupOption() {
        return cupOption;
    }

    public void setCupOption(String cupOption) {
        this.cupOption = cupOption;
    }

    public String getSizeOption() {
        return sizeOption;
    }

    public void setSizeOption(String sizeOption) {
        this.sizeOption = sizeOption;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
