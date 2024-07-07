package gift.Repository;

import gift.DTO.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

  private final JdbcTemplate jdbcTemplate;

  public UserDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createUser(UserDto userInfo) {
    var sql = "INSERT INTO USERS(email,password) VALUES(?,?)";
    jdbcTemplate.update(sql, userInfo.getEmail(), userInfo.getPassword());
  }

  public UserDto getUserByEmail(String email) {
    var sql = "SELECT * FROM USERS WHERE email = ?";
    return jdbcTemplate.queryForObject(sql, new String[] { email }, userRowMapper());
  }

  private RowMapper<UserDto> userRowMapper() {
    return (rs, rowNum) -> new UserDto(
        rs.getString("email"),
        rs.getString("password"));
  }
}
