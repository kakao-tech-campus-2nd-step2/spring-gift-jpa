package gift.domain;

import jakarta.persistence.*;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private Long menuId;

    public WishList() {}

    public WishList(Long id, String memberId, Long menuId){
        this.id = id;
        this.memberId = memberId;
        this.menuId = menuId;
    }
}
