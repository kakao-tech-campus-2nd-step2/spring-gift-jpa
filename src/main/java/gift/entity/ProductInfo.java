package gift.entity;

public class ProductInfo{
    private String ProductName;
    private String ImageUrl;
    private Integer Count;
    private int Price;

    public ProductInfo() {}

    public ProductInfo(String productName, int price, String imageUrl, Integer count) {
        ProductName = productName;
        Price = price;
        ImageUrl = imageUrl;
        Count = count;
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