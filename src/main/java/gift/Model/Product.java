package gift.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="product")
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="name")
        @Size(max=15, message = "글자의 길이는 15를 넘을 수 없습니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = "허용되지 않은 특수 기호((),[],+,-,&,/,_ 이외)가 있습니다.")
        private String name;

        @Column(name="price")
        private int price;

        @Column(name = "image_url")
        private String imageUrl;

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
        public Product(){}

        public Product(String name, int price, String imageUrl){
                this.name = name;
                this.price = price;
                this.imageUrl = imageUrl;
        }
}


