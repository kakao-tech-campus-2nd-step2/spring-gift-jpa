package gift.dto;

public class WishRequestDto {
    private String productName;
    private int quantity;

    public WishRequestDto(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
