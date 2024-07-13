package gift.entity;

import jakarta.persistence.*;

@Entity
public class Wish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String memberEmail;

  @Column(nullable = false)
  private Long productId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMemberEmail() {
    return memberEmail;
  }

  public void setMemberEmail(String memberEmail) {
    this.memberEmail = memberEmail;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }
}
