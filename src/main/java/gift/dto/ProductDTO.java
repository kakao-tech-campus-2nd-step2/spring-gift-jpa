package gift.dto;


import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public final class ProductDTO {

    private final Long id;

    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Length(min = 1, max = 15, message = "제품명 길이는 1~15자만 가능합니다.")
    @Pattern(regexp = "[a-zA-Z0-9ㄱ-ㅎ가-힣()\\[\\]+\\-&/_ ]+", message = "( ), [ ], +, -, &, /, _을 제외한 특수문자는 입력할 수 없습니다.")
    @Pattern(regexp = "^((?!카카오).)*$", message = "카카오가 포함된 문구는 담당 MD와 협의한 후에 사용해주시기 바랍니다.")
    private final String name;

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 0 이상, 2147483647 이하이여야 합니다.")
    @Max(value = Integer.MAX_VALUE, message = "가격은 0 이상, 2147483647 이하이여야 합니다.")
    private final Integer price;

    @NotBlank(message = "이미지 URL을 입력해주세요.")
    @URL(message = "URL 형식이 아닙니다.")
    private final String imageUrl;

    public Long getId() {
        return id;
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

    public ProductDTO(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ProductDTO) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.price, that.price) &&
                Objects.equals(this.imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }

    public static ProductDTO convertToProductDTO(Product product) throws BadRequestException {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Product convertToProduct(){
        return new Product(this.name, this.price, this.imageUrl);
    }
}
