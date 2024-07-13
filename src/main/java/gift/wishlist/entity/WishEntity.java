package gift.wishlist.entity;

import gift.member.entity.MemberEntity;
import gift.product.entity.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish")
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public WishEntity() {
    }

    public WishEntity(MemberEntity memberEntity, ProductEntity productEntity) {
        this.memberEntity = memberEntity;
        this.productEntity = productEntity;

        memberEntity.getWishEntityList().add(this);
        productEntity.getWishEntityList().add(this);
    }

    public Long getId() {
        return id;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void updateMemberEntity(MemberEntity memberEntity) {
        // 기존 memberEntity에서  wishEntity 삭제
        this.memberEntity.getWishEntityList().remove(this);
        this.memberEntity = memberEntity;
        memberEntity.getWishEntityList().add(this);
    }

    public void updateProductEntity(ProductEntity productEntity) {
        // 기존 productEntity에서 wishEntity 삭제
        this.productEntity.getWishEntityList().remove(this);
        this.productEntity = productEntity;
        productEntity.getWishEntityList().add(this);
    }
}
