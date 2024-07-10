package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "product")
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false, name = "id")
        private long id;

        @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
        @Pattern(
                regexp = "^[\\w\\s()\\[\\]+\\-&/_]+$",
                message = "상품 이름에 허용되지 않는 특수 문자가 포함되어 있습니다."
        )
        @Column(nullable = false , name = "name")
        private String name;

        @Min(value = 1, message = "가격은 1 이상이어야 합니다.")
        @Column(nullable = false, name = "price")
        private int price;

        @Column(nullable = false, name = "image_url")
        private String imageUrl;

        public Product() {}

        public Product(long id, String name, int price, String imageUrl) {
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

        public int getPrice() {
                return price;
        }

        public void setPrice(int price) {
                this.price = price;
        }

        public String getImageUrl() {
                return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
        }
}
