package gift;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class Product {
    private final Long id;

    @NotBlank(message = "상품 이름은 필수 입력 항목입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[\\p{L}0-9 ()\\[\\]+\\-&/_]+$", message = "상품 이름에 사용 가능한 특수문자는 ( ), [ ], +, -, &, /, _ 입니다")
    @Pattern(regexp = "^(?!.*(?i)(kakao|카카오).*$).*$", message = "상품 이름에 '카카오'를 사용할 수 없습니다.")
    private final String name;

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private final BigDecimal price;
    private final String imageUrl;
    private final String description;

    public Product() {
        this.id = null;
        this.name = null;
        this.price = null;
        this.imageUrl = null;
        this.description = null;
    }

    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
    }

    public Long getId() {
        return id;
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

    public static class ProductBuilder {
        private Long id;
        private String name;
        private BigDecimal price;
        private String imageUrl;
        private String description;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
