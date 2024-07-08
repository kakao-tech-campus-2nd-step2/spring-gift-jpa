package gift.request;

import gift.domain.Product;
import gift.constant.ErrorMessage;
import gift.validation.product.KakaoNotAllowed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ProductRequest {

    @NotBlank(message = ErrorMessage.PRODUCT_NAME_NOT_BLANK)
    @Length(max = 15, message = ErrorMessage.PRODUCT_NAME_EXCEEDS_MAX_LENGTH)
    @Pattern(regexp = "[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*", message = ErrorMessage.PRODUCT_NAME_INVALID_CHAR)
    @KakaoNotAllowed
    private String name;

    @NotNull(message = ErrorMessage.PRODUCT_PRICE_NOT_NULL)
    private Integer price;

    private String imageUrl;

    public Product toEntity() {
        return new Product(this.name, this.price, this.imageUrl);
    }

    public ProductRequest() {
    }

    public ProductRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

}
