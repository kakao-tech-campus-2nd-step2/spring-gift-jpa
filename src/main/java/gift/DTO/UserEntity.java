package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;

@Entity
@Table
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;

  public UserEntity() {

  }

  public UserEntity(Long id, String email, String password) {
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

  public boolean matchLoginInfo(UserDto userByEmail) {
    return this.email.equals(userByEmail.getEmail()) && this.password.equals(
      userByEmail.getPassword());
  }

}
