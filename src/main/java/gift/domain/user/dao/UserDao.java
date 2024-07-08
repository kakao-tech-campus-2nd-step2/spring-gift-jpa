package gift.domain.user.dao;

import gift.domain.user.dto.UserDto;
import gift.domain.user.entity.User;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcClient jdbcClient;

    public UserDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public User insert(User user) {
        String sql = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
            .param(user.getName())
            .param(user.getEmail())
            .param(user.getPassword())
            .param(user.getRole().name())
            .update(keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return user;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";

        Optional<UserDto> userDto = jdbcClient.sql(sql)
            .param(email)
            .query(UserDto.class)
            .optional();

        if (userDto.isEmpty()) {
            return Optional.empty();
        }
        return userDto.map(UserDto::toUser);
    }

    public Optional<User> findById(long userId) {
        String sql = "SELECT * FROM user WHERE id = ?";

        Optional<UserDto> userDto = jdbcClient.sql(sql)
            .param(userId)
            .query(UserDto.class)
            .optional();

        if (userDto.isEmpty()) {
            return Optional.empty();
        }
        return userDto.map(UserDto::toUser);
    }

    public Optional<User> update(User user) {
        String sql = "UPDATE user SET name = ?, password = ?, role = ? WHERE email = ?";

        int nOfRowsAffected = jdbcClient.sql(sql)
            .param(user.getName())
            .param(user.getPassword())
            .param(user.getRole().name())
            .param(user.getEmail())
            .update();

        if (nOfRowsAffected <= 0) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public Integer delete(String email) {
        String sql = "DELETE FROM user WHERE email = ?";

        return jdbcClient.sql(sql)
            .param(email)
            .update();
    }
}
