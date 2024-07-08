package gift.dto;

public class WishDto {
    private Long productId;
    private String productName;
    private int amount;

    public WishDto(Long productId,String productName, int amount){
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
    }

    public Long getProductId(){
        return productId;
    }

    public String getProductName(){
        return productName;
    }

    public int getAmount(){
        return amount;
    }
}
