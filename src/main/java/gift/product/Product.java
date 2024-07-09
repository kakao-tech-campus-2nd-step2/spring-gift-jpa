package gift.product;

import jakarta.persistence.*;

@Entity
public class Product {
    @Column(nullable = false)
    private int price;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(nullable = false, unique = true, length = 15)
    private String name;
    @Column(nullable = false)
    private String imageUrl;

    public Product(){}

    public Product(int price,String name,String imageUrl){
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void update(int price, String name, String imageUrl) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public long getId(){return id;}

    public int getPrice(){ return price;}

    public String getName(){
        return name;
    }

    public String getImageUrl(){
        return imageUrl;
    }
}
