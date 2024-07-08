package gift.repository;

import gift.model.User;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userRowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
            );
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM Users", userRowMapper);
    }

    public User findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Users WHERE id = ?", userRowMapper, id);
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO Users (email, password) VALUES (?, ?)",
            user.getEmail(), user.getPassword());
    }

    public void update(Long id, User user) {
        jdbcTemplate.update("UPDATE Users SET email = ?, password = ? WHERE id = ?",
            user.getEmail(), user.getPassword(), id);
    }
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Users WHERE id = ?", id);
    }
    public Long getIdByEmailPassword(String email, String password){
        String sql = "SELECT id FROM Users WHERE email = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, email, password);
    }
}
