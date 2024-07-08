package gift.domain.product;

import gift.validation.ValidProductName;
import jakarta.validation.constraints.*;

public class ProductRequestDTO {

    @NotBlank(message = "상품 이름은 필수입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣()\\[\\]+\\-&/_]*$", message = "상품 이름에 허용되지 않는 특수 문자가 포함되어 있습니다.")
    @ValidProductName //카카오 검증
    private String name;

    @NotNull(message = "상품 가격은 필수입니다.")
    @Positive(message = "상품 가격은 양수이어야 합니다.")
    private Long price;

    @NotBlank(message = "상품 설명은 필수입니다.")
    private String description;

    private String imageUrl; // 이미지 URL


    public ProductRequestDTO(String name, Long price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}