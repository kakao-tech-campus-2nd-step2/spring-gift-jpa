package gift.product.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import gift.product.dto.ProductReqDto;
import gift.wishlist.entity.WishList;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private  Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<WishList> wishLists = new ArrayList<>();

    public Product(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    protected Product() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void update(ProductReqDto productReqDto) {
        this.name = productReqDto.name();
        this.price = productReqDto.price();
        this.imageUrl = productReqDto.imageUrl();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
