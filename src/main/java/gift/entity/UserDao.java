package gift.entity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User insertUser(User user) {
        var sql = "insert into \"user\" (email, password) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.email);
            ps.setString(2, user.password);
            return ps;
        }, keyHolder);
        Long userId = keyHolder.getKey().longValue();
        return new User(userId, user.email, user.password);
    }

    public Optional<User> selectUserByEmail(String email) {
        var sql = "select id, email, password from \"user\" where email = ?";
        List<User> users = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ),
                email
        );
        return users.stream().findFirst();
    }

    public Optional<User> selectUserById(Long id) {
        var sql = "select id, email, password from \"user\" where id = ?";
        List<User> users = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ),
                id
        );
        return users.stream().findFirst();
    }

    public List<User> selectAllUsers() {
        var sql = "select id, email, password from \"user\"";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                )
        );
    }

    public void updateUser(User user) {
        var sql = "update \"user\" set email = ?, password = ? where id = ?";
        jdbcTemplate.update(sql, user.email, user.password, user.id);
    }

    public void deleteUser(Long id) {
        var sql = "delete from \"user\" where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
