package gift.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL)
    private List<WishEntity> wishEntityList;

    public MemberEntity() {
    }

    public MemberEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.wishEntityList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<WishEntity> getWishEntityList() {
        return wishEntityList;
    }

    public void addWishEntity(WishEntity wishEntity) {
        this.wishEntityList.add(wishEntity);
        wishEntity.updateMemberEntity(this);
    }

    public void removeWishEntity(WishEntity wishEntity) {
        this.wishEntityList.remove(wishEntity);
    }
}
