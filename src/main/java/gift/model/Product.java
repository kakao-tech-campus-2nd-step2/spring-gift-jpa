package gift.model;


import gift.exception.InputException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected Product() {
    }

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, Integer price, String imageUrl) {
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateProduct(String newName, Integer newPrice, String newImageUrl) {
        validateName(newName);
        validatePrice(newPrice);
        validateImageUrl(newImageUrl);
        this.name = newName;
        this.price = newPrice;
        this.imageUrl = newImageUrl;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 15) {
            throw new InputException("1~15자 사이로 입력해주세요.");
        }
        if (!name.matches("^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9()+&/_ \\[\\]-]*$")) {
            throw new InputException("상품명에 특수 문자는 '(, ), [, ], +, -, &, /, -' 만 입력 가능합니다.");
        }
    }

    private void validatePrice(Integer price) {
        if (price == null) {
            throw new InputException("가격을 입력해주세요.");
        }
        if (price < 100 || price > 1000000) {
            throw new InputException("상품 가격은 100원~1,000,000원 사이여야 합니다.");
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new InputException("이미지 주소를 입력해주세요.");
        }
        if (!imageUrl.matches("^(https?)://[^ /$.?#].[^ ]*$")) {
            throw new InputException("올바른 url이 아닙니다.");
        }
    }
}
