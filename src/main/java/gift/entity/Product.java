package gift.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private Long price;

    @OneToMany(mappedBy = "product")
    private List<Wish> wishes;

    public Product() {
    }

    public Product(Long id, String name, Long price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public Product(String name, Long price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public void update(String name, Long price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public List<Wish> getWishes() {
        return wishes;
    }
}
