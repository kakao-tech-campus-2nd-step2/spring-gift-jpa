package gift.request;

import gift.domain.Product;
import gift.validation.product.KakaoNotAllowed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class ProductRequest {

    @NotBlank
    @Length(max = 15)
    @Pattern(regexp = "[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*")
    @KakaoNotAllowed
    private String name;

    @NotNull
    private Integer price;

    @NotBlank
    private String imageUrl;

    public Product toEntity() {
        return new Product(this.name, this.price, this.imageUrl);
    }

    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

}
