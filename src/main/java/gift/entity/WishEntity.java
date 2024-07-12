package gift.entity;

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

    public void updateMemberEntity(MemberEntity memberEntity){
        // 기존의 memberEntity의 wishEntityList에 있던 wishEntity 삭제 필요

        this.memberEntity = memberEntity;
        memberEntity.getWishEntityList().add(this);
    }

    public void updateProductEntity(ProductEntity productEntity){
        this.productEntity = productEntity;
        productEntity.getWishEntityList().add(this);
    }
}
