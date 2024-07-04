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
    var sql = "INSERT INTO USERS(username,pw) VALUES(?,?)";
    jdbcTemplate.update(sql, userInfo.getUsername(), userInfo.getPw());
  }

  public LoginDto UserLogin(String username, String pw){
    var sql = "SELECT * FROM USERS WHERE username = ?";
    return jdbcTemplate.queryForObject(sql, new String[]{username},userRowMapper());
  }

  private RowMapper<LoginDto> userRowMapper() {
    return (rs,rowNum)-> new LoginDto(
      rs.getString("username"),
      rs.getString("pw"),
      rs.getString("accessToken")
    );
  }
}
