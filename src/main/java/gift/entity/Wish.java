package gift.entity;

import jakarta.persistence.*;

@Entity
public class Wish {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    private long productId;

    public Wish() {}

    public Wish(Member member, long productId) {
        this.member = member;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return member.getId();
    }

    public long getProductId() {
        return productId;
    }
}
