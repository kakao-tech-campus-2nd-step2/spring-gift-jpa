package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gift")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private int price;
    @Column(name = "imageUrl",nullable = false)
    private String imageUrl;

    public Gift() {
    }

    public Gift( String name, int price, String imageUrl) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("카카오 문구는 MD와 협의 후 사용가능합니다.");
        }
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


    private boolean isValidName(String name) {
        return name != null && !name.contains("카카오");
    }

    public void modifyGift(String name,int price,String imageUrl){
        this.name = name;
        this.price= price;
        this.imageUrl =imageUrl;
    }
}