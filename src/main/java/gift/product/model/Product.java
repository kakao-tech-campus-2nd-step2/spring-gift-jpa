package gift.product.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Product {
    private long id;

    @NotBlank(message = "상품의 이름은 필수 항목입니다.")
    @Size(max = 15, message = "상품의 이름은 최대 15자까지 입력할 수 있습니다.")
    private String name;

    @Min(value = 0, message = "상품의 가격은 0보다 크거나 같아야 합니다.")
    private int price;

    private String imgUrl;

    public Product(){}

    public Product(long id, String name, int price, String imgUrl) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.imgUrl=imgUrl;
    }

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
}
