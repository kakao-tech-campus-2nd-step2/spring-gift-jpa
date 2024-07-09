package gift.dto.response;

public class WishProductResponse {

    Long productId;
    String productName;
    int productPrice;
    String productImageUrl;
    int productAmount;

    public WishProductResponse(Long productId, String productName, int productPrice, String productImageUrl, int productAmount) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
        this.productAmount = productAmount;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public int getProductAmount() {
        return productAmount;
    }

}
