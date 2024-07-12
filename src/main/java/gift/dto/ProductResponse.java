package gift.dto;

import java.util.List;

public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private List<WishResponse> wishes;

    public ProductResponse(Long id, String name, int price, String imageUrl, List<WishResponse> wishes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.wishes = wishes;
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

    public List<WishResponse> getWishes() {
        return wishes;
    }
}
