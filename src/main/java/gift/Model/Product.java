package gift.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Product {

    private Long id;
    @NotBlank(message = "입력은 공백일 수 없습니다.")
    @Size(max = 15, message = "길이가 15를 넘을 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ()\\[\\]+&/_ ]*$", message = "( ), [ ], +, -, &, /, _ 외의 특수 문자는 사용이 불가합니다.")
    @Pattern(regexp = "(?!.*카카오).*$",message = "\"카카오\"가 포함된 문구입니다. 담당 MD와 협의 하세요")
    private String name;
    @Positive(message = "상품의 가격은 0이하일 수 없습니다.")
    private int price;
    private String imageUrl;

    public Product() {
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
