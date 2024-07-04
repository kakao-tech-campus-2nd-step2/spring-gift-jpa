package gift.main.repository;

import gift.main.dto.UserDto;
import gift.main.dto.UserJoinRequest;
import gift.main.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createUserTable();
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "role VARCHAR(255) NOT NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public User selectUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                long userId = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                return new User(userId, name, email, password, role);
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // 해당 id의 User가 존재하지 않는 경우
        }
    }

    public void insertUser(UserDto user) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(),user.getEmail(),user.getPassword(),user.getRole());
    }

    public void updateUser(long id, User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getRole(), id);
    }

    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsUserByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }
}
