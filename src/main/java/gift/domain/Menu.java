package gift.domain;

import jakarta.validation.constraints.NotNull;

public class Menu {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public Menu(String name, int price, String imageUrl) {
        this(null,name,price,imageUrl);
    }

    public Menu(Long id, String name, int price, String imageUrl) {
        this.id = id;
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

}
