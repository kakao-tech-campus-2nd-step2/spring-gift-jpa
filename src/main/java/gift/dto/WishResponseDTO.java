package gift.dto;

public class WishResponseDTO{

    private String email;
    private String ProductName;
    private String ImageUrl;
    private Integer Count;
    private int Price;

    public WishResponseDTO() {
    }

    public WishResponseDTO(String email, String productName, String imageUrl, Integer count, int price) {
        this.email = email;
        this.ProductName = productName;
        this.ImageUrl = imageUrl;
        this.Count = count;
        this.Price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
