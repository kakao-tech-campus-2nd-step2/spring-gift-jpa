package gift.wishlist.model;

import gift.member.model.Member;
import jakarta.persistence.*;

import javax.naming.Name;

@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // wishlist 와 member 의 다대일 관계 매핑
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String product;

    // JPA 에서
    protected WishList() {
    }

    public WishList(Member member, String name, String product) {
        this.member = member;
        this.name = name;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getProduct() {
        return product;
    }

    public String name() {
        return name;
    }
}