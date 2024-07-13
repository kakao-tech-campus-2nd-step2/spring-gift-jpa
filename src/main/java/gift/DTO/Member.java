package gift.DTO;

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
@Table
public class Member {

  @OneToMany(mappedBy = "member")
  private final List<WishList> wishLists = new ArrayList<>();
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;

  public Member() {

  }

  public Member(Long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }


  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public boolean matchLoginInfo(MemberDto memberDtoByEmail) {
    return this.email.equals(memberDtoByEmail.getEmail()) && this.password.equals(
      memberDtoByEmail.getPassword());
  }

}
