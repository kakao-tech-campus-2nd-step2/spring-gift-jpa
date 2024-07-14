package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "상품 이름을 입력해주세요.")
        @Size(max = 255, message = "상품 이름은 255자 이내여야 합니다.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\(\\)\\[\\]+&\\-/_]*$", message = "상품 이름에 유효하지 않은 문자가 포함되어 있습니다.")
        @Column(nullable = false)
        private String name;

        @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
        @Column(nullable = false)
        private int price;

        @Column(nullable = false, name = "image_url")
        private String imageUrl;

        protected Product() {}

        public Product(Long id, String name, int price, String imageUrl) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.imageUrl = imageUrl;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
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