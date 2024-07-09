package gift.dto;

public class WishResponse {
    private Long id;
    private String productName;
    private int productPrice;
    private String productImageurl;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageurl() {
        return productImageurl;
    }

    public void setProductImageUrl(String productImageurl) {
        this.productImageurl = productImageurl;
    }
}