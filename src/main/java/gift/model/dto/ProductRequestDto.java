package gift.model.dto;

import gift.exception.ProductErrorCode;
import gift.exception.ProductException;
import gift.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductRequestDto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 15, message = "상품 이름은 공백을 포함하여 최대 15자입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 \\(\\)\\[\\]\\+\\-\\&/_]*$", message = "사용가능한 특수 문자는 (),[],+,-,&,/,_ 입니다.")
    private String name;

    @NotNull
    @Min(value = 0, message = "가격은 0원 이상입니다.")
    private int price;

    @NotBlank
    private String imageUrl;

    public ProductRequestDto() {
    }

    private ProductRequestDto(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
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

    public Product toEntity() throws ProductException {
        validateKakaoWord(name);
        return new Product(
            id,
            name,
            price,
            imageUrl
        );
    }

    public static ProductRequestDto from(Product product) {
        return new ProductRequestDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    private void validateKakaoWord(String name) throws ProductException {
        if (name.contains("카카오")) {
            throw new ProductException(ProductErrorCode.HAS_KAKAO_WORD);
        }
    }
}
