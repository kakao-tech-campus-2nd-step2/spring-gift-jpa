package gift.product.dto;

import gift.product.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductRequest {
    private long id;

    @NotBlank(message = "상품의 이름은 필수 항목입니다.")
    @Size(max = 15, message = "상품의 이름은 최대 15자까지 입력할 수 있습니다.")
    private String name;

    @Min(value = 0, message = "상품의 가격은 0보다 크거나 같아야 합니다.")
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
