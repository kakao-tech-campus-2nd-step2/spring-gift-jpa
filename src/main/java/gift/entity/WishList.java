package gift.entity;

import gift.dto.WishListDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish_list")
public class WishList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    public WishList() {}

    public WishList(Member member, Long productId){
        this.member =member;
        this.productId = productId; 
    }

    public Long getId() {
        return id;
    }

    public Member getMember(){
        return member;
    }

    public Long getProductId() {
        return productId;
    }

    public WishListDto toDto(){
        return new WishListDto(this.member.getId(), this.productId);
    }

}
