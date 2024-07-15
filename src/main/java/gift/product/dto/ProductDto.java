package gift.product.dto;
import gift.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  @NotBlank(message = "이름을 입력해야 합니다.")
  @Size(max = 15, message = "이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
  @Pattern(regexp = "^[\\w \\[\\]\\(\\)\\+\\-\\&\\/\\_가-힣]*$", message = "이름에는 특수 문자는 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다.")
  @Pattern(regexp = "^(?!.*카카오).*$", message = "이름에 '카카오' 포함은 불가합니다. 예외적 사용 시 담당 MD의 사전 승인이 필수입니다.")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @NotNull(message = "가격을 입력해야 합니다.")
  @Min(value = 1, message = "가격은 양수여야 합니다.")
  @Column(name = "price", nullable = false)
  private int price;

  @NotBlank(message = "이미지 URL을 입력해야 합니다.")
  @Column(name = "imageUrl", nullable = false)
  private String imageUrl;

  public ProductDto(Long id, String name, int price, String imageUrl) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
  }

  public ProductDto() {

  }

  public static ProductDto fromEntity(Product product) {
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getImageUrl()
    );
  }

  public Product toEntity() {
    Product product = new Product();
    product.setId(this.id);
    product.setName(this.name);
    product.setPrice(this.price);
    product.setImageUrl(this.imageUrl);
    return product;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
