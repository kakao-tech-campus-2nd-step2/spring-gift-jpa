package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wish_lists")
public class WishListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = MemberEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public WishListEntity() {

    }

    public WishListEntity(MemberEntity memberEntity, ProductEntity productEntity) {
        this.memberEntity = memberEntity;
        this.productEntity = productEntity;
    }

    public WishListEntity(Long id, MemberEntity memberEntity, ProductEntity productEntity) {
        this.id = id;
        this.memberEntity = memberEntity;
        this.productEntity = productEntity;
    }

    public Long getId() {
        return id;
    }

    public MemberEntity getMemberEntity() {
        return memberEntity;
    }

    public void setMemberId(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductId(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

}
