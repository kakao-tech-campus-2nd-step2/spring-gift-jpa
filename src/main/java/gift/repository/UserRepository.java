package gift.repository;

import gift.DTO.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_USER_SQL = "INSERT INTO users (email, password) VALUES (?, ?)";
    private static final String SELECT_USER_BY_EMAIL_SQL = "SELECT * FROM users WHERE email = ?";

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUser(User user) {
        jdbcTemplate.update(INSERT_USER_SQL, user.getEmail(), user.getPassword());
    }

    public Optional<User> findUserByEmail(String email) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_SQL, new UserRowMapper(), email));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                rs.getString("email"),
                rs.getString("password")
            );
        }
    }

}