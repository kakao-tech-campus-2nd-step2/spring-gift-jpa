package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product")
    private List<Wishlist> wishlists;

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

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void validate() {
        validateNullName();
        validateEmptyName();
        validateLengthName();
        validateInvalidName();
        validateKaKaoName();
        validateNullPrice();
        validateEmptyPrice();
        validateInvalidPrice();
        validateNullImageUrl();
        validateEmptyImageUrl();
        validateInvalidImageUrl();
    }

    private void validateNullName() {
        if (name == null) {
            throw new IllegalArgumentException("상품 이름은 최소 1자 이상이어야 합니다.");
        }
    }

    private void validateEmptyName() {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 최소 1자 이상이어야 합니다.");
        }
    }

    private void validateLengthName() {
        if (name.length() > 15) {
            throw new IllegalArgumentException("상품 이름은 공백 포함 최대 15자까지 입력할 수 있습니다.");
        }
    }

    private void validateInvalidName() {
        if (!name.matches("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$")) {
            throw new IllegalArgumentException("상품 이름에 (), [], +, -, &, /, _ 외 특수 문자는 사용할 수 없습니다.");
        }
    }

    private void validateKaKaoName() {
        if (name.contains("카카오")) {
            throw new IllegalArgumentException("'카카오'가 포함된 문구는 담당 MD와 협의 후 사용 바랍니다.");
        }
    }

    private void validateNullPrice() {
        if (price == null) {
            throw new IllegalArgumentException("가격을 입력해야 합니다.");
        }
    }

    private void validateEmptyPrice() {
        if (price.trim().isEmpty()) {
            throw new IllegalArgumentException("가격을 입력해야 합니다.");
        }
    }

    private void validateInvalidPrice() {
        if (!price.matches("^\\d+$")) {
            throw new IllegalArgumentException("가격은 0이상의 숫자만 입력 가능합니다.");
        }
    }

    private void validateNullImageUrl() {
        if (imageUrl == null) {
            throw new IllegalArgumentException("이미지 URL을 입력해야 합니다.");
        }
    }

    private void validateEmptyImageUrl() {
        if (imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("이미지 URL을 입력해야 합니다.");
        }
    }

    private void validateInvalidImageUrl() {
        if (!imageUrl.matches("^(http|https)://.*$")) {
            throw new IllegalArgumentException("유효한 이미지 URL을 입력해야 합니다.");
        }
    }
}