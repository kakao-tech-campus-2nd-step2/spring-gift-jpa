package gift.repository;

import gift.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    public User save(User user) {
        try {
            Map<String, Object> parameters = Map.of(
                    "email", user.getEmail(),
                    "password", user.getPassword()
            );
            Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
            Long id = newId.longValue();
            return new User(id, user.getEmail(), user.getPassword());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<User> findAll() {
        var sql = "select * from users";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                )
        );
    }

    public User findByUserEmail(String email) {
        var sql = "select * from users where email=?";
        List<User> results = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ),
                email
        );
        return results.isEmpty() ? null : results.get(0);
    }
}
