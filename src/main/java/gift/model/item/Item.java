package gift.model.item;

import gift.model.wishList.WishItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    private String imgUrl;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<WishItem> wishes = new ArrayList<>();

    public Item() {
    }

    public Item(Long id, String name, Long price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<WishItem> getWishes() {
        return wishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(
            price, item.price) && Objects.equals(imgUrl, item.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imgUrl);
    }

    public ItemDTO toItemDTO() {
        return new ItemDTO(id, name, price, imgUrl);
    }
}
