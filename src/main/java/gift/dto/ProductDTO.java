package gift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gift.model.Product;
import gift.model.ProductName;

public class ProductDTO {
    @JsonProperty
    private long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer price;
    @JsonProperty
    private String imageUrl;

    public ProductDTO(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.setName(name);
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        ProductName productName = new ProductName(name);
        this.name = productName.getName();
    }

    public static ProductDTO getProductDTO(Product product){
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
