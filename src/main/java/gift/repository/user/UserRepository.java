package gift.repository.user;

import gift.domain.Product;
import gift.domain.User;
import gift.utils.JwtUtil;
import java.util.List;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JwtUtil jwtUtil;

    public UserRepository(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.jwtUtil = jwtUtil;
    }

    public User findByEmail(String email) {
        var sql = "select * from users where email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? null : users.get(0);
    }

    public String registerUser(String name, String email, String password) {
        User user = new User(name, email, password, "ROLE_USER");
        save(user);
        return jwtUtil.generateToken(email); // 회원 가입 후 토큰 생성하여 반환
    }

    public void save(User user) {
        var sql = "insert into users (name, email, password, role) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(),user.getEmail(), user.getPassword(), user.getRole());
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
        rs.getString("name"),
        rs.getString("email"),
        rs.getString("password"),
        rs.getString("role")
    );

}
