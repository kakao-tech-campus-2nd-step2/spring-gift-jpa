package gift.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class ProductDTO implements Serializable {

    private Long id;

    @Valid
    @NotNull(message = "이름을 입력해주세요.")
    private Name name;
    private int price;
    private String imageUrl; // image_url 사용

    // 기본 생성자
    public ProductDTO() {
        this.name = new Name(); // Name 객체 초기화
    }

    public ProductDTO(Long id, Name name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl; // image_url 사용
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }


    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }


    public String getImageUrl() {
        return imageUrl;
    }

}