package gift.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false, length = 15)
    private String name;
    @Column(name="price", nullable = false)
    private int price;
    @Column(name="image_url", nullable = false)
    private String imageUrl;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    public void addWish(Wish wish) {
        this.wishes.add(wish);
        wish.setProduct(this);
    }

    public void removeWish(Wish wish) {
        wish.setProduct(null);
        this.wishes.remove(wish);
    }

    public void removeWishes() {
        Iterator<Wish> iterator = this.wishes.iterator();

        while(iterator.hasNext()) {
            Wish wish = iterator.next();

            wish.setProduct(null);
            iterator.remove();
        }
    }

    protected Product () {
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl){
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

    public List<Wish> getWishes() {
        return wishes;
    }
}
