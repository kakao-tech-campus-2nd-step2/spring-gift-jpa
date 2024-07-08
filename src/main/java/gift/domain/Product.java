package gift.domain;

import gift.response.ProductResponse;

public class Product {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public Product() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public ProductResponse toDto() {
        return new ProductResponse(this.getId(), this.getName(), this.getPrice(),
            this.getImageUrl());
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product product = (Product) o;

        if (price != product.price) {
            return false;
        }
        if (id != null ? !id.equals(product.id) : product.id != null) {
            return false;
        }
        if (name != null ? !name.equals(product.name) : product.name != null) {
            return false;
        }
        return imageUrl != null ? imageUrl.equals(product.imageUrl) : product.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

}
