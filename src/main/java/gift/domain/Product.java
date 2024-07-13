package gift.domain;

import gift.dto.request.ProductRequestDto;
import gift.utils.TimeStamp;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private int price;
    private String imageUrl;

    @OneToMany(mappedBy = "product")
    private List<Wish> wishList = new ArrayList<>();

    public Product() {
    }

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.imageUrl = builder.imageUrl;
    }

    public static class Builder {
        private String name;
        private int price;
        private String imageUrl;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
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

    public List<Wish> getWishList() {
        return wishList;
    }

    public void update(ProductRequestDto productDto){
        this.name = productDto.name();
        this.price = productDto.price();
        this.imageUrl = productDto.imageUrl();
    }
}
