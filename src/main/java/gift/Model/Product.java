package gift.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)

    @NotBlank(message = "입력은 공백일 수 없습니다.")
    @Size(max = 15, message = "길이가 15를 넘을 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ()\\[\\]+&/_ ]*$", message = "( ), [ ], +, -, &, /, _ 외의 특수 문자는 사용이 불가합니다.")
    @Pattern(regexp = "(?!.*카카오).*$",message = "\"카카오\"가 포함된 문구입니다. 담당 MD와 협의 하세요")
    private String name;

    @Column(name = "price", nullable = false)
    @Positive(message = "상품의 가격은 0이하일 수 없습니다.")
    private int price;

    @Column(name = "imageUrl", nullable = false)

    private String imageUrl;

    protected Product(){
        
    }

    @ConstructorProperties({"id","name","price","imageUrl"})
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

}
