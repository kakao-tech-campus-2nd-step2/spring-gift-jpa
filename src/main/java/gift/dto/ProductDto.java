package gift.dto;

import gift.entity.Product;

public class ProductDto {
    private long id;
    private String name;
    private String url;
    private int price;

    public ProductDto(String name, int price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public ProductDto(long id, String name, int price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public static ProductDto fromEntity(Product product) {
        return new ProductDto(product.getName(), product.getPrice(), product.getUrl());
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

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
