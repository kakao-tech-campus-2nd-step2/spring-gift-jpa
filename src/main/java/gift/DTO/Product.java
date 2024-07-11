package gift.DTO;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private int price;
  private String imageUrl;

  @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
  private List<WishList> wishlists = new ArrayList<>();

  public Product() {
  }

  public Product(Long id, String name, int price, String imageUrl) {
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
