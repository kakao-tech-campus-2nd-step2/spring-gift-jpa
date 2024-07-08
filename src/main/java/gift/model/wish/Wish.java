package gift.model.wish;

public class Wish {
    private Long productId;
    private String productName;
    private int amount;

    public Wish(Long productId,String productName,int amount){
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
