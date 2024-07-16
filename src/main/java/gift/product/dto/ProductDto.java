package gift.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

public class ProductDto {
    private Long product_id;

    @NotBlank(message = "상품명을 입력하세요.")
    private String name;

    @NotNull(message = "가격을 입력하세요.")
    private int price;

    @NotBlank(message = "이미지 URL을 입력하세요.")
    @URL(message = "잘못된 URL 입니다.")
    private String imgUrl;

    public ProductDto() {}

    public ProductDto(Long product_id, String name, int price, String imgUrl) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public void product_id() {
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public int price() {
        return price;
    }

    public void price(int price) {
        this.price = price;
    }

    public String imgUrl() {
        return imgUrl;
    }

    public void imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}