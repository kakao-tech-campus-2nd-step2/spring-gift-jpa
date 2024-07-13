package gift.model;

import gift.exception.KakaoValidationException;
import gift.exception.StringValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {

  private Long id;

  @Size(max = 15, message = "상품 이름은 최대 15자까지 가능합니다.")
  @NotBlank
  private String name;

  @NotNull(message = "가격은 필수 항목입니다.")
  private int price;

  @NotBlank
  private String imageUrl;

  public ProductDto() {
  }

  public ProductDto(Long id, String name, int price, String imageUrl) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
    validate();
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

  public int getPrice() {
    return price;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void validate() {
    if (name.contains("카카오")) {
      throw new KakaoValidationException("상품 이름에 '카카오'를 포함하려면 담당 MD와 협의가 필요합니다.");
    } else if (!name.matches("^[\\p{L}\\p{N}\\s\\(\\)\\[\\]\\+\\-\\&\\/]*$")) {
      throw new StringValidationException("허용되지 않은 특수기호는 사용할 수 없습니다. 허용된 특수기호:( ), [ ], +, -, &, /, _");
    }
  }
}
