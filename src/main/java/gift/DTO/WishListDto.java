package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class WishListDto {
  private Long id;

  private Long userId;
  private Long productId;

  public WishListDto() {
  }

  public WishListDto(Long id, Long userId, Long productId){
    this.id=id;
    this.userId=userId;
    this.productId=productId;
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
