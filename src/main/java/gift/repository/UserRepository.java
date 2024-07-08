package gift.repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import gift.exception.CustomException.UserNotFoundException;
import gift.exception.ErrorCode;
import gift.model.user.User;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(User user) {
        var sql = "insert into users (email,password) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassWord());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey().longValue());
    }

    public User findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(
                "select * from users where email =?",
                (rs, row) -> new User(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
                ),
                email
            );
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException(ErrorCode.EMAIL_NOT_FOUND);
        }
    }

    public User findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(
                "select * from users where id =?",
                (rs, row) -> new User(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
                ),
                id
            );
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException(ErrorCode.EMAIL_NOT_FOUND);
        }
    }

    public List<User> findAll() {
        return jdbcTemplate.query(
            "select* from users",
            (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
            )
        );
    }

    public void delete(String email) {
        jdbcTemplate.update("delete from users where email = ?", email);
    }

}
