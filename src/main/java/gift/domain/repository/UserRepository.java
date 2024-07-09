package gift.domain.repository;

import gift.domain.dto.UserPermissionChangeRequestDto;
import gift.domain.dto.UserRequestDto;
import gift.domain.entity.User;
import gift.global.util.HashUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> getRowMapper() {
        return (resultSet, rowNum) -> new User(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("permission")
        );
    }

    public User save(UserRequestDto requestDto) {
        String sql = "INSERT INTO users (email, password, permission) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, requestDto.email(), HashUtil.hashCode(requestDto.password()), "user");
        return findByEmail(requestDto.email()).orElseThrow(RuntimeException::new);
    }

    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getRowMapper(), email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> updatePassword(UserRequestDto requestDto) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        jdbcTemplate.update(sql, HashUtil.hashCode(requestDto.password()), requestDto.email());
        return findByEmail(requestDto.email());
    }

    public Optional<User> updatePermission(UserPermissionChangeRequestDto requestDto) {
        String sql = "UPDATE users SET permission = ? WHERE email = ?";
        jdbcTemplate.update(sql, requestDto.permission(), requestDto.email());
        return findByEmail(requestDto.email());
    }

    public void deleteByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }
}
