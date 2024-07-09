package gift.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Wish> wishes;

    public Product() {}

    public Product(Long id, String name, Integer price, String imageUrl, Set<Wish> wishes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.wishes = wishes;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Integer price() {
        return price;
    }

    public String imageUrl() {
        return imageUrl;
    }

    public Set<Wish> wishes() {
        return wishes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Product) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.price, that.price) &&
            Objects.equals(this.imageUrl, that.imageUrl) &&
            Objects.equals(this.wishes, that.wishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl, wishes);
    }

    @Override
    public String toString() {
        return "Product[" +
            "id=" + id + ", " +
            "name=" + name + ", " +
            "price=" + price + ", " +
            "imageUrl=" + imageUrl + ", " +
            "wishes=" + wishes + ']';
    }
}
