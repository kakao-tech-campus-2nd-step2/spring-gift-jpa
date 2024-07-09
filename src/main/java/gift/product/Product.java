package gift.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Product {

    @NotNull(message = "id는 필수 값 입니다.")
    @Id
    private Long id;

    @NotNull(message = "상품 명은 필수 값 입니다.")
    @Size(min = 1, max = 15, message = "상품 명의 길이는 1~15자 입니다.")
    @Pattern(regexp = "^[A-Za-z가-힣0-9()\\[\\]\\-&/_+\\s]*$", message = "( ), [ ], +, -, &, /, _ 를 제외한 특수문자는 사용할 수 없습니다.")
    private String name;

    @NotNull(message = "가격은 필수 값 입니다.")
    private int price;

    private String imageUrl;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
