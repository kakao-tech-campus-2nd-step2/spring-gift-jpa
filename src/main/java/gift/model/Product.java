package gift.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name="products")
@Validated
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 15, message = "Name must be up to 15 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ()\\[\\]+\\-&/_ㄱ-ㅣ가-힣]+$", message = "Special characters allowed: (),[],+,-,&,/,_")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "Name cannot contain '카카오'. Please consult with the MD.")
    private String name;
    @NotNull
    private long price;
    @NotNull
    private String imageUrl;

    public Product() {}

    public Product(long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
