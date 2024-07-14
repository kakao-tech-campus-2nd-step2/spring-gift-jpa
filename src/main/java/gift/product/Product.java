package gift.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Kakao
    private String name;

    private int price;

    private String imageUrl;

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    protected Product(){
    }
    public Product(Long id, String name, int price, String imageUrl){
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void update(String name, int price, String imageUrl){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
