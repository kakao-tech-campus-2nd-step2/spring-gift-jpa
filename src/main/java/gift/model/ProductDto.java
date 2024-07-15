package gift.model;

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

  public ProductDto(Long id, String name, int price, String imageUrl) {
    this.id = id;
    this.name = name;
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
