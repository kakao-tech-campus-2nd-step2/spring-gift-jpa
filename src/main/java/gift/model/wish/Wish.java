package gift.model.wish;

import jakarta.persistence.*;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Long memberId;
    @Column(nullable = false)
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
