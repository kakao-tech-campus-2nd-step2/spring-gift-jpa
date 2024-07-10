package gift.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    @Size(max = 15, message = "?�품 ?�름?� 최�? 15?�까지 ?�력?????�습?�다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9 ()\\[\\]+,&/_-]*$",
            message = "?�품 ?�름?�는 ?�문?? ?�자, 공백, (), [], +, -, &, /, _ �??�용?????�습?�다."
    )
    public String name;

    @NotNull(message = "?�품 가격�? ?�수 ??��?�니??")
    public Integer price;

    @NotEmpty(message = "?��?지 URL?� ?�수 ??��?�니??")
    public String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("카카?")) {
            throw new IllegalArgumentException("?�품 ?�름??'카카??가 ?�함??경우 ?�당 MD?� ?�의가 ?�요?�니??");
        }
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

