package gift.wish.dto;

import gift.product.dto.ProductDto;
import gift.user.dto.UserDto;
import jakarta.validation.constraints.NotNull;

public class WishDto {

  private Long id;

  @NotNull(message = "사용자는 필수 입력 항목입니다.")
  @NotNull
  private UserDto user;

  @NotNull(message = "상품은 필수 입력 항목입니다.")
  @NotNull
  private ProductDto product;

  public WishDto(Long id, UserDto user, ProductDto product) {
    this.id = id;
    this.user = user;
    this.product = product;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public ProductDto getProduct() {
    return product;
  }

  public void setProduct(ProductDto product) {
    this.product = product;
  }
}
