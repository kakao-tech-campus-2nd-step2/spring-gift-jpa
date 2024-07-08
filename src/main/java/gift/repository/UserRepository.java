package gift.repository;

import gift.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String userInsertSql = "INSERT INTO USERS (email, password, role) VALUES (?, ?, ?)";
    private static final String userSelectSql = "SELECT * FROM USERS WHERE email = ?";
    private static final String userCountSql = "SELECT COUNT(*) FROM USERS WHERE email = ?";

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertUser(User user) {
        jdbcTemplate.update(userInsertSql, user.getEmail(), user.getPassword(), "user");
    }

    public User selectUser(String email) {
        User user = jdbcTemplate.queryForObject(
                userSelectSql, new Object[]{email}, (resultSet, rowNum) -> {
                    User userEntity = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role")
                    );
                    return userEntity;
                });

        return user;
    }

    public Optional<Integer> countUsers(String email) {
        try {
            Integer count = jdbcTemplate.queryForObject(userCountSql, new Object[]{email}, Integer.class);
            return Optional.ofNullable(count);
        } catch (EmptyResultDataAccessException e) {
            return Optional.of(0);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

}
