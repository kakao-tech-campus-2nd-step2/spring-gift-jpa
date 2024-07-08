package gift.domain.product;

import gift.global.annotation.NotContainsValue;

public class Product {

    private Long id;

    @NotContainsValue(value = "카카오", message = "'{value}' 가 포함된 문구는 담당 MD 와 협의 후 사용 가능합니다.")
    private String name;
    private int price;
    private String imageUrl;

    public Product() {

    }

    public Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
    }

}
