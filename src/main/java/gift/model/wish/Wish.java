package gift.model.wish;

import jakarta.persistence.*;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long memberId;
    private int amount;

    public Wish(Long productId, Long memberId, int amount){
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
