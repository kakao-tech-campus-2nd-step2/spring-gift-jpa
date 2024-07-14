package gift.product.dto;

import gift.product.model.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imgUrl;

    public ProductResponse(Long id, String name, int price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImgUrl());
    }
}
