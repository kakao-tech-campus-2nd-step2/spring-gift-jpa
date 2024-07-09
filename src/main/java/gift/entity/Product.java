package gift.entity;

import gift.dto.ProductDTO;
import gift.exception.BadRequestExceptions.BlankContentException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class Product {

    private Integer id;

    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Length(min = 1, max = 15, message = "제품명 길이는 1~15자만 가능합니다.")
    @Pattern(regexp = "[a-zA-Z0-9ㄱ-ㅎ가-힣()\\[\\]+\\-&/_ ]+", message = "( ), [ ], +, -, &, /, _을 제외한 특수문자는 입력할 수 없습니다.")
    @Pattern(regexp = "^((?!카카오).)*$", message = "카카오가 포함된 문구는 담당 MD와 협의한 후에 사용해주시기 바랍니다.")
    private String name;

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 0 이상, 2147483647 이하이여야 합니다.")
    @Max(value = Integer.MAX_VALUE, message = "가격은 0 이상, 2147483647 이하이여야 합니다.")
    private Integer price;

    @NotBlank(message = "이미지 URL을 입력해주세요.")
    @URL(message = "URL 형식이 아닙니다.")
    private String imageUrl;

    private Integer quantity;

    public Product() {
    }

    public Product(Integer id, String name, Integer price, String imageUrl) {
        this(id, name, price, imageUrl, 0);
    }

    public Product(ProductDTO productDTO) {
        this(productDTO.id(), productDTO.name(), productDTO.price(), productDTO.imageUrl(), 0);
        blankCatch(productDTO);
    }

    public Product(Integer id, String name, Integer price, String imageUrl, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }


    public Integer getId() {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getQuantity());
    }

    private void blankCatch(ProductDTO productDTO) throws BlankContentException {
        if (productDTO.name().isBlank() || productDTO.price() == null || productDTO.imageUrl()
                .isBlank()) {
            throw new BlankContentException("입력 값에 빈 곳이 있습니다. 다시 입력해주세요");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
