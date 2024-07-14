package gift.model.wish;

import gift.model.member.Member;
import gift.model.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @ManyToOne
    @JoinColumn(name = "products_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int amount;

    public Wish(){

    }

    public Wish(Product product, Member member, int amount){
        this.product = product;
        this.member = member;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId(){
        return product.getId();
    }

    public Long getMemberId(){
        return member.getId();
    }

    public int getAmount(){
        return amount;
    }
}
