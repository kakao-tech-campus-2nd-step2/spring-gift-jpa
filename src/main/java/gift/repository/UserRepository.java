package gift.repository;

import gift.model.User;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (resultSet, rowNum) ->
        new User(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("role")
        );

    public User save(User user) {
        jdbcTemplate.update("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
            user.name(), user.email(), user.password(), user.role());
        return user;
    }

    public User findByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", userRowMapper,
            email);
        return users.isEmpty() ? null : users.get(0);
    }
}