package gift.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public class Product {
    private long id;

    @Size(max = 15, message = "Name must be up to 15 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ()\\[\\]+\\-&/_ㄱ-ㅣ가-힣]+$", message = "Special characters allowed: (),[],+,-,&,/,_")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "Name cannot contain '카카오'. Please consult with the MD.")
    private String name;
    private long price;
    private String imageUrl;

    public Product(long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
