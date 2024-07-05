package gift.Repository;

import gift.DTO.LoginDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

  private final JdbcTemplate jdbcTemplate;

  public LoginDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void UserSignUp(LoginDto userInfo) {
    var sql = "INSERT INTO USERS(email,password) VALUES(?,?)";
    jdbcTemplate.update(sql, userInfo.getEmail(), userInfo.getPassword());
  }

  public LoginDto UserLogin(String email, String password){
    var sql = "SELECT * FROM USERS WHERE email = ?";
    return jdbcTemplate.queryForObject(sql, new String[]{email},userRowMapper());
  }

  private RowMapper<LoginDto> userRowMapper() {
    return (rs,rowNum)-> new LoginDto(
      rs.getString("email"),
      rs.getString("password")
    );
  }
}
