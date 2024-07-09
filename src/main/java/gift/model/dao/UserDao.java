package gift.model.dao;

import static gift.model.dao.UserQuery.DELETE_USER;
import static gift.model.dao.UserQuery.INSERT_USER;
import static gift.model.dao.UserQuery.SELECT_USER_BY_USERNAME_AND_PASSWORD;

import gift.model.Role;
import gift.model.User;
import gift.model.repository.UserRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User entity) {
        if (entity.isNew()) {
            jdbcTemplate.update(INSERT_USER, entity.getUsername(), entity.getPassword(), entity.getRole().name());
            return;
        }
        update(entity);
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(UserQuery.UPDATE_USER, entity.getUsername(), entity.getPassword(), entity.getRole().name(),
                entity.getId());
    }

    @Override
    public Optional<User> find(Long aLong) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(UserQuery.SELECT_USER_BY_ID, new Object[]{aLong},
                    (rs, rowNum) -> UserMapper(rs)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(User entity) {
        jdbcTemplate.update(DELETE_USER, entity.getId());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(UserQuery.SELECT_ALL_USERS, (rs, rowNum) -> UserMapper(rs));
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_USER_BY_USERNAME_AND_PASSWORD,
                    new Object[]{username, password}, (rs, rowNum) -> UserMapper(rs)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(UserQuery.SELECT_USER_BY_USERNAME, new Object[]{username},
                            (rs, rowNum) -> UserMapper(rs))
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private User UserMapper(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                Role.of(rs.getString("role"))
        );
    }
}
