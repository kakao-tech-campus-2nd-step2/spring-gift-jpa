package gift.DTO;


import jakarta.validation.constraints.NotBlank;

public class WishListDto {

  private Long id;
  @NotBlank
  private Long userId;
  @NotBlank
  private Long productId;

  public WishListDto() {
  }

  public WishListDto(Long id, Long userId, Long productId) {
    this.id = id;
    this.userId = userId;
    this.productId = productId;
  }

  public Long getId() {
    return this.id;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getProductId() {
    return this.productId;
  }

}
