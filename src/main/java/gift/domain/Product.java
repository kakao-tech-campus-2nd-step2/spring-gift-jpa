package gift.domain;

import gift.web.dto.request.product.UpdateProductRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ColumnDefault("'https://gift-s3.s3.ap-northeast-2.amazonaws.com/default-image.png'")
    private URL imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<WishProduct> wishProducts = new ArrayList<>();

    protected Product() {
    }

    public static class Builder extends BaseEntity.Builder<Builder> {

        private String name;
        private Integer price;
        private URL imageUrl;


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Integer price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(URL imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Product build() {
            return new Product(this);
        }
    }

    private Product(Builder builder) {
        super(builder);
        name = builder.name;
        price = builder.price;
        imageUrl = builder.imageUrl;
    }

    public Product update(UpdateProductRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.imageUrl = request.getImageUrl();
        return this;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

}
