package gift.repository;

import gift.domain.User;
import gift.dto.UserLogin;
import gift.dto.UserSignUp.Request;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Request request, String uuid) {
        String sql = "INSERT INTO user_tb (email, password, accessToken) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, request.getEmail(), request.getPassword(), uuid);
    }

    public Optional<String> findByEmailAndPassword(UserLogin.Request request) {
        String sql = "SELECT * FROM user_tb WHERE email=? AND password=?";

        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper(), request.getEmail(), request.getPassword());
            return Optional.ofNullable(user.getAccessToken());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user_tb WHERE email=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public long findIdByAccessToken(String accessToken) {
        String sql = "SELECT * FROM user_tb WHERE accessToken=?";
        User user = jdbcTemplate.queryForObject(sql, userRowMapper(), accessToken);
        return user.getId();
    }
    private static RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return User.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .accessToken(rs.getString("accessToken"))
                    .build();
            }
        };
    }
}
