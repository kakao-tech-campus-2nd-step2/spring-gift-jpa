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

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private final List<WishList> wishlists = new ArrayList<>();
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private int price;
  @Column(nullable = false)
  private String imageUrl;

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
