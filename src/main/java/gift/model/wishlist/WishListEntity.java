package gift.model.wishlist;

import gift.DTO.ProductDTO;
import gift.auth.DTO.MemberDTO;
import gift.model.member.MemberEntity;
import gift.model.product.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WishListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JoinColumn(name = "product_id")
    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    private ProductDTO productDTO;

    @Column(nullable = false)
    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = MemberEntity.class, fetch = FetchType.LAZY)
    private MemberDTO memberDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public MemberDTO getMemberDTO() {
        return memberDTO;
    }

    public void setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }
}
