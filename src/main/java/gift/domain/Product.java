package gift.domain;

import java.net.URL;

public class Product extends BaseEntity {

    private final String name;
    private final Integer price;
    private final URL imageUrl;

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
