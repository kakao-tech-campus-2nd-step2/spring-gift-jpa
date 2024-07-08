package gift.product.model.dto;

public class Product {
    private Long id;        // 선물의 고유 식별자
    private String name;    // 선물의 이름
    private int price = 0;      // 선물의 가격
    private String imageUrl; // 선물 이미지 URL
    private boolean isActive = true; // 선물의 활성화 상태

    public Product(Long id, String name, int price, String imageUrl, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}