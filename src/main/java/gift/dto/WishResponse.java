package gift.dto;

public class WishResponse {
    private Long id;
    private String productName;
    private int productPrice;
    private String productImageurl;

    public WishResponse() {
    }

    private WishResponse(Builder builder) {
        this.id = builder.id;
        this.productName = builder.productName;
        this.productPrice = builder.productPrice;
        this.productImageurl = builder.productImageurl;
    }


    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImageurl() {
        return productImageurl;
    }


    public static class Builder {
        private Long id;
        private String productName;
        private int productPrice;
        private String productImageurl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productPrice(int productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public Builder productImageurl(String productImageurl) {
            this.productImageurl = productImageurl;
            return this;
        }

        public WishResponse build() {
            return new WishResponse(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}
