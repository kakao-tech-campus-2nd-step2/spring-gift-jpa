package gift.product.model.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;    // 선물의 이름

    @Column(nullable = false)
    private int price = 0;      // 선물의 가격

    @Column(name = "image_url")
    private String imageUrl; // 선물 이미지 URL

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // 선물의 활성화 상태

    public Product(Long id, String name, int price, String imageUrl, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    public Product() {

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

    public boolean isActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}