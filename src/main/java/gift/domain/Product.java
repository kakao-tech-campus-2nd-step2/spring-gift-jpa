package gift.domain;


import gift.dto.ProductRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private long id;
    private String name;
    private int price;
    private String imageUrl;

    public Product() {
        this.id = -1;
        this.name = "";
        this.price = 0;
        this.imageUrl = "";
    }

    public Product(long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = Math.max(price,0);
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        this.id = -1;
        this.name = name;
        this.price = Math.max(price, 0);
        this.imageUrl = imageUrl;
    }

    public static Product fromEntity(ProductRequestDTO requestDTO){
        return new Product(requestDTO.getName(), requestDTO.getPrice(), requestDTO.getImageUrl());
    }
}

