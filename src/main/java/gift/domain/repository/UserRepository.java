package gift.domain.repository;

import gift.domain.model.User;
import gift.domain.model.UserRequestDto;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isExistEmail(String email) {
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)",
                Boolean.class, email));
    }

    public void save(UserRequestDto userRequestDto) {
        jdbcTemplate.update(
            "INSERT INTO users (email, password) VALUES (?, ?)",
            userRequestDto.getEmail(),
            userRequestDto.getPassword()
        );
    }

    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(
                "SELECT email, password FROM users WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) ->
                    new User(
                        rs.getString("email"),
                        rs.getString("password")
                    )
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
