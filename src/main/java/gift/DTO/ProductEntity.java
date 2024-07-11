package gift.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table()
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


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
