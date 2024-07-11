package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class WishListEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private Long userId;

  @Column(unique = true, nullable = false)
  private Long productId;

  public WishListEntity() {
  }

  public WishListEntity(Long id, Long userId, Long productId) {
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
