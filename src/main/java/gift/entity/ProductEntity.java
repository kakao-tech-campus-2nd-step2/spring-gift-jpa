package gift.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<WishEntity> wishEntityList;

    public ProductEntity() {
    }

    public ProductEntity(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.wishEntityList = new ArrayList<>();
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

    public List<WishEntity> getWishEntityList() {
        return wishEntityList;
    }

    public void update(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void addWishEntity(WishEntity wishEntity) {
        this.wishEntityList.add(wishEntity);
        wishEntity.updateProductEntity(this);
    }

    public void removeWishEntity(WishEntity wishEntity) {
        this.wishEntityList.remove(wishEntity);
    }
}
