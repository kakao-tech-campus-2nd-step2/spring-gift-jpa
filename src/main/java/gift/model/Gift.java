package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "gift")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "price")
    @NotNull
    private int price;
    @Column(name = "imageUrl")
    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "gift", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes;

    public Gift() {
    }

    public Gift(String name, int price, String imageUrl) {
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

    public void modifyGift(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}