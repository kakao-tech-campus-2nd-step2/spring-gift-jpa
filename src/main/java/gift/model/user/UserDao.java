package gift.model.user;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private static final String USER_INSERT = "INSERT INTO users (email, password, name, role) VALUES (?, ?, ?,?)";
    private static final String USER_SELECT_BY_ID = "SELECT id, email, password, name, role FROM users WHERE id = ?";
    private static final String USER_SELECT_BY_EMAIL = "SELECT id, email, password, name, role FROM users WHERE email = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = new UserRowMapper();

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(User user) {
        jdbcTemplate.update(USER_INSERT, user.getEmail(), user.getPassword(), user.getName(),
            Role.USER.toString());
    }

    public Optional<User> findById(String id) {
        try {
            User user = jdbcTemplate.queryForObject(USER_SELECT_BY_ID,
                userRowMapper, id);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(USER_SELECT_BY_EMAIL,
                userRowMapper, email);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
