package gift.dto;

import gift.domain.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductDTO {

    @NotBlank(message = "상품 이름은 필수 입력 항목입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[\\p{L}0-9 ()\\[\\]+\\-&/_]+$", message = "상품 이름에 사용 가능한 특수문자는 ( ), [ ], +, -, &, /, _ 입니다")
    @Pattern(regexp = "^(?!.*(?i)(kakao|카카오).*$).*$", message = "상품 이름에 '카카오'를 사용할 수 없습니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    private String imageUrl;

    private String description;

    private ProductDTO(ProductDTOBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public static class ProductDTOBuilder {
        private String name;
        private BigDecimal price;
        private String imageUrl;
        private String description;

        public ProductDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDTOBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this);
        }
    }

    public Product toEntity() {
        return new Product.ProductBuilder()
            .name(this.name)
            .price(this.price)
            .imageUrl(this.imageUrl)
            .description(this.description)
            .build();
    }
}
