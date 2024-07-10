package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "상품을 입력해주세요")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다")
    @Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]+$", message = "특수문자는 (),[],+,-,&,/,_만 가능합니다")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "상품 이름에 '카카오'를 포함할 수 없습니다. 관리자와의 협의가 필요합니다.")
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String imageurl;

    @OneToMany(mappedBy = "product")
    private List<Wish> wishes;


    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.imageurl = builder.imageurl;
        this.wishes = builder.wishes;
    }

    public Product() {

    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public static class Builder {
        private Long id;
        private String name;
        private Integer price;
        private String imageurl;
        private List<Wish> wishes;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Integer price) {
            this.price = price;
            return this;
        }

        public Builder imageurl(String imageurl) {
            this.imageurl = imageurl;
            return this;
        }

        public Builder wishes(List<Wish> wishes) {
            this.wishes = wishes;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}
