package gift.model.wish;

import gift.dto.WishDto;
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

    public void updateWish(WishDto wishDto){
        this.product = wishDto.getProduct();
        this.member = wishDto.getMember();
        this.amount = wishDto.getAmount();
    }

    public Product getProduct(){
        return product;
    }

    public Member getMember(){
        return member;
    }

    public int getAmount(){
        return amount;
    }
}
