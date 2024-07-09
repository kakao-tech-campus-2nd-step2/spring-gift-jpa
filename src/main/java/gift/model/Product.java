package gift.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Product {
    private Long id;

    @NotBlank(message = "이름은 공백이 될 수 없습니다.")
    @Size(max = 15, message = "이름은 15자를 넘길 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]\\+\\-\\&/_]*$", message = "이름에 허용되지 않은 특수문자가 포함되어 있습니다.(가능한 특수문자: ( ), [ ], +, -, &, /, _)")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'라는 문구를 사용하시려면 담당 MD에게 문의 부탁드립니다.")
    private String name;

    @Min(value = 1, message = "가격은 1 미만이 될 수 없습니다.")
    private int price;

    @NotBlank(message = "Image URL을 입력해주세요.")
    private String imageUrl;

    public Product(){

    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
