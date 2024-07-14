package gift.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", columnDefinition = "varchar(15) not null")
    private String name;

    @Column(name = "price", columnDefinition = "integer not null")
    private Integer price;

    @Column(name = "image_url", columnDefinition = "varchar(255) not null")
    private String imageUrl;

    public Product(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.setName(name);
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        this.setName(name);
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product update(String name, Integer price, String imageUrl){
        if(!name.isEmpty()){
            this.setName(name);
        }
        if(price != null){
            this.price = price;
        }
        if(!imageUrl.isEmpty()){
            this.imageUrl = imageUrl;
        }
        return this;
    }

    protected Product() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        ProductName productName = new ProductName(name);
        this.name = productName.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }
}
