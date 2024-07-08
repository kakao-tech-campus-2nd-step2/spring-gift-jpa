package gift.model;

import gift.exception.KakaoValidationException;
import gift.exception.StringValidationException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class Product {

  private Long id;

  @Size(max = 15, message = "상품 이름은 최대 15자까지 가능합니다.")
  @NotBlank
  private String name;
  private int price;
  private String imageUrl;

  public Product() {
  }

  public Product(String name, int price, String imageUrl) {
    setName(name);
    this.price = price;
    this.imageUrl = imageUrl;
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
    if (name.contains("카카오")) {
      throw new KakaoValidationException("상품 이름에 '카카오'를 포함하려면 담당 MD와 협의가 필요합니다.");
    }
    else if(!name.matches("^[\\p{L}\\p{N}\\s\\(\\)\\[\\]\\+\\-\\&\\/]*$")) {
      throw new StringValidationException("허용되지 않은 특수기호는 사용할 수 없습니다. 허용된 특수기호:( ), [ ], +, -, &, /, _");
    }
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