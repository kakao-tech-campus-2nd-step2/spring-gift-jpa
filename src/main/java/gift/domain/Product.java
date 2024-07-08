package gift.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Product {
    private Long id;
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 15, message = "이름의 최대 글자수는 15입니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\(\\)\\[\\]\\+\\-\\&\\/\\_\\s]*$",
        message = "상품 이름은 최대 15자, 한글과 영문, 그리고 특수기호([],(),+,-,&,/,_)만 사용 가능합니다!"
    )
    @Pattern(
        regexp = "(?!.*카카오).*",
        message = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다!"
    )
    private String name;
    private int price;
    private String imageUrl;

    public Product(){}
    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
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
