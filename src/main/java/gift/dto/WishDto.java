package gift.dto;

public class WishDto {
    private Long productId;
    private Long memberId;
    private int amount;

    public WishDto(Long productId,Long memberId, int amount){
        this.productId = productId;
        this.memberId = memberId;
        this.amount = amount;
    }

    public Long getProductId(){
        return productId;
    }

    public Long getMemberId(){
        return memberId;
    }

    public int getAmount(){
        return amount;
    }
}
