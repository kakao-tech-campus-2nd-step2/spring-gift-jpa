package gift.dto;

import gift.entity.Product;

public class ProductDetailDTO {
    private String name, url;
    private int price;

    public ProductDetailDTO(String name, int price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public static ProductDetailDTO fromEntity(Product product) {
        return new ProductDetailDTO(product.getName(), product.getPrice(), product.getUrl());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
