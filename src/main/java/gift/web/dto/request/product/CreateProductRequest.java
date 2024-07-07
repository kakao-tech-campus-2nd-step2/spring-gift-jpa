package gift.web.dto.request.product;

import gift.domain.Product;
import gift.web.validation.constraints.RequiredKakaoApproval;
import gift.web.validation.constraints.SpecialCharacter;
import jakarta.validation.constraints.NotBlank;
import java.net.URL;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class CreateProductRequest {

    @NotBlank
    @Length(min = 1, max = 15)
    @SpecialCharacter(allowed = "(, ), [, ], +, -, &, /, _")
    @RequiredKakaoApproval
    private final String name;
    @Range(min = 1000, max = 10000000)
    private final Integer price;
    private final URL imageUrl;

    public CreateProductRequest(String name, Integer price, URL imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public Product toEntity() {
        return new Product.Builder()
            .name(this.name)
            .price(this.price)
            .imageUrl(this.imageUrl)
            .build();
    }
}
