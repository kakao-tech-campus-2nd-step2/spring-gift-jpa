package gift.repository.user;

import gift.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
    }

    private static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password")
    );

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, userRowMapper, email);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User user) {
        String INSERT_USER = "INSERT INTO users (email, password) VALUES (?, ?)";
        jdbcTemplate.update(INSERT_USER, user.getEmail(), user.getPassword());
    }

    @Override
    public void update(User user) {
        String UPDATE_USER = "UPDATE users SET email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_USER, user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public void delete(Long id) {
        String DELETE_USER = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public Optional<User> findById(Long id) {
        String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(SELECT_BY_ID, userRowMapper, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String COUNT_BY_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
        int count = jdbcTemplate.queryForObject(COUNT_BY_EMAIL, Integer.class, email);
        return count > 0;
    }
}
