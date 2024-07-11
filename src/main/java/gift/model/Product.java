package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 15, nullable = false)
    @NotBlank(message = "상품 이름은 최소 1자 이상이어야 합니다.")
    @Size(max = 15, message = "상품 이름은 공백 포함 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$", message = "상품 이름에 (), [], +, -, &, /, _ 외 특수 문자는 사용할 수 없습니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의 후 사용 바랍니다.")
    private String name;

    @Column(name = "price", nullable = false)
    @NotBlank(message = "가격을 입력해야 합니다.")
    @Pattern(regexp = "^\\d+$", message = "가격은 0이상의 숫자만 입력 가능합니다.")
    private String price;

    @Column(name = "image_url", nullable = false)
    @NotBlank(message = "이미지 URL을 입력해야 합니다.")
    @Pattern(regexp = "^(http|https)://.*$", message = "유효한 이미지 URL을 입력해야 합니다.")
    private String imageUrl;

    protected Product() {
    }

    public Product(Long id, String name, String price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank(message = "상품 이름은 최소 1자 이상이어야 합니다.") @Size(max = 15, message = "상품 이름은 공백 포함 최대 15자까지 입력할 수 있습니다.") @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$", message = "상품 이름에 (), [], +, -, &, /, _ 외 특수 문자는 사용할 수 없습니다.") @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의 후 사용 바랍니다.") String getName() {
        return name;
    }

    public @NotBlank(message = "가격을 입력해야 합니다.") @Pattern(regexp = "^\\d+$", message = "가격은 0이상의 숫자만 입력 가능합니다.") String getPrice() {
        return price;
    }

    public @NotBlank(message = "이미지 URL을 입력해야 합니다.") @Pattern(regexp = "^(http|https)://.*$", message = "유효한 이미지 URL을 입력해야 합니다.") String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price='" + price + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
    }
}