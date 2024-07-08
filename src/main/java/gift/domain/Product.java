package gift.domain;

import jakarta.validation.constraints.*;

import static gift.constant.Message.*;

public class Product {

    @NotNull(message = REQUIRED_FIELD_MSG)
    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    private Long id;

    @NotEmpty(message = REQUIRED_FIELD_MSG)
    @Size(max = 15, message = LENGTH_ERROR_MSG)
    private String name;

    @NotNull(message = REQUIRED_FIELD_MSG)
    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    private int price;
    private String imageUrl;

    public Product() {}

    public Product(long id, String name, int price, String imageUrl) {
        this.id =  id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}