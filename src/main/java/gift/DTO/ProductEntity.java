package gift.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table()
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, max = 15, message = "가능한 글자 수는 1~15입니다.")
  @Pattern.List({
    @Pattern(regexp = "^[가-힣a-zA-Z0-9()\\[\\]+\\-&/_]*$", message = "유효한 이름이 아닙니다"),
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오' 포함된 경우 담당 MD와 협의가 필요합니다.")
  })
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private int price;
  private String imageUrl;

  public ProductEntity() {
  }

  public ProductEntity(Long id, String name, int price, String imageUrl) {
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


  public int getPrice() {
    return price;
  }


  public String getImageUrl() {
    return imageUrl;
  }

}
