package gift.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "product")
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false, name = "id")
        private Long id;

        @Column(nullable = false , name = "name")
        private String name;

        @Column(nullable = false, name = "price")
        private int price;

        @Column(nullable = false, name = "image_url")
        private String imageUrl;

        public Product() {}

        public Product(Long id, String name, int price, String imageUrl) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.imageUrl = imageUrl;
        }

        public Long getId() {
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
