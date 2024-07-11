package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;

  public User() {

  }

  public User(Long id, String email, String password) {
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

  public boolean matchLoginInfo(UserDto userDtoByEmail) {
    return this.email.equals(userDtoByEmail.getEmail()) && this.password.equals(
      userDtoByEmail.getPassword());
  }

}
