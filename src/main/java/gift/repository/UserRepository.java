package gift.repository;

import gift.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
        rs.getString("email"),
        rs.getString("password")
    );

    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<User> findByEmail(String email) {
        List<User> results = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", userRowMapper, email);
        return results.stream().findFirst();
    }

    // 사용자 저장, 업데이트
    public void save(User user) {
        if (findByEmail(user.getEmail()).isEmpty()) {
            jdbcTemplate.update("INSERT INTO users (email, password) VALUES (?, ?)",
                user.getEmail(), user.getPassword());
        } else {
            jdbcTemplate.update("UPDATE users SET email = ?, password = ? WHERE email = ?",
                user.getEmail(), user.getPassword(), user.getEmail());
        }
    }
}
