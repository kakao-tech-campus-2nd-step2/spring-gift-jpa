package gift.Repository;

import gift.DTO.LoginDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

  private final JdbcTemplate jdbcTemplate;

  public LoginDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void UserSignUp(LoginDto userInfo) {
    var sql = "INSERT INTO USER(username,pw) VALUES(?,?)";
    jdbcTemplate.update(sql, userInfo.getUsername(), userInfo.getPw());
  }
}
