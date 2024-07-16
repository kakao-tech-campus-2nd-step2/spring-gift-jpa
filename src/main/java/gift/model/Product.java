package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(name = "image_url")
    private String imageUrl;

    public Product() {}

    public Product(Long id, String name, int price, String imageUrl) {
        if (name.contains("카카오") || name.equalsIgnoreCase("kakao")) {
            throw new IllegalArgumentException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에 사용 가능합니다.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        if (name.contains("카카오") || name.equalsIgnoreCase("kakao")) {
            throw new IllegalArgumentException("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에 사용 가능합니다.");
        }
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
