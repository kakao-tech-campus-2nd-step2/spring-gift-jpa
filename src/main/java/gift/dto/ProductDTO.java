package gift.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    private Long id;

    @Valid
    @NotNull(message = "이름을 입력해주세요.")
    private NameDTO name;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotNull(message = "이미지 URL을 입력해주세요.")
    private String imageUrl;

    public ProductDTO() {}

    public ProductDTO(Long id, NameDTO name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NameDTO getName() {
        return name;
    }

    public void setName(NameDTO name) {
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