package gift.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 15, message = "이름은 최대 15글자입니다.")
    @Pattern(regexp = "^[A-Za-z0-9 ()\\[\\]+\\-&/_ㄱ-ㅣ가-힣]+$", message = "특수문자는 (),[],+,-,&,/,_만 허용됩니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 들어간 제품명은 MD와 상의해주세요.")
    private String name;
    @NotNull
    private long price;
    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "product")
    private List<WishlistItem> wishlistItemList;

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
    public List<WishlistItem> getWishlistItemList() {
        return wishlistItemList;
    }

    public void setWishlistItemList(List<WishlistItem> wishlistItemList) {
        this.wishlistItemList = wishlistItemList;
    }
}
