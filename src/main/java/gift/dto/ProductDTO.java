package gift.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class ProductDTO implements Serializable {

    private Long id;

    @Valid
    @NotNull(message = "이름을 입력해주세요.")
    private Name name;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotNull(message = "이미지 URL을 입력해주세요.")
    private String imageUrl;

    // 기본 생성자
    public ProductDTO() {}

    public ProductDTO(Long id, Name name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}