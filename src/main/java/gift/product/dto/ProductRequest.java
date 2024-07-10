package gift.product.dto;

import gift.product.model.Product;

public class ProductRequest {
    private long id;
    private String name;

    private int price;

    private String imgUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public static ProductRequest from(Product product) {
        ProductRequest request = new ProductRequest();
        request.setId(product.getId());
        request.setName(product.getName());
        request.setPrice(product.getPrice());
        request.setImgUrl(product.getImgUrl());
        return request;
    }
}
