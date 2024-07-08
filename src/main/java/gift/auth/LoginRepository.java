package gift.auth;

import gift.DTO.UserDTO;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    private final JdbcTemplate jdbcTemplate;

    public LoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserDTO> getUser(Login login) {
        String sql = "SELECT * FROM Users WHERE email = ? and password = ? and isDelete=0";
        return Optional.of(
            jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDTO.class),
                login.getEmail(), login.getPassword()));
    }
}
