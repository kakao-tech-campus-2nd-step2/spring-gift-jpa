package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int price;
  private String imageUrl;

  public Product() {
  }

  public Product(String name, int price, String imageUrl) {
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
  }
  public void setId(Long id) {
    this.id = id;
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

  public void update(String name, int price, String imageUrl) {
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
  }
}
