package gift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductRequest {

    @NotBlank
    @Size(max=15, message = "최대 15자리까지 입력하실 수 있습니다.")
    @Pattern(regexp = "[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/가-힣]*", message = "특수 문자는 '(), [], +, -, &, /, _ '만 사용가능 합니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "담당 MD와 협의해 주세요.")
    private String name;

    @NotNull
    private int price;

    @NotNull
    private String imageUrl;

    public ProductRequest(){}

    public ProductRequest(String name, int price, String imageUrl){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getPrice() {
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
