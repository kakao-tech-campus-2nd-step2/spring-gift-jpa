package gift;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDto {

    private Long id;

    @Size(min=0, max = 15, message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣()*\\[\\]+\\-&/_]*$", message = "( ), [ ], +, -, &, /, _ 외 특수 문자 사용 불가능합니다. ")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능합니다")
    private String name;

    private double price;

    private String imageUrl;

    public ProductDto(Long id, String name, double price, String imageUrl) {
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

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
