package gift.repository;

import gift.domain.UserInfo;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<UserInfo> UserRowMapper = (rs, rowNum) -> new UserInfo(
        rs.getString("password"),
        rs.getString("email")
    );

    public Optional<UserInfo> findByEmail(String email) {
        String sql = "SELECT * FROM UserInfo WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, UserRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Boolean save(UserInfo userInfo) {
        String sql = "INSERT INTO UserInfo (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, userInfo.getEmail(), userInfo.getPassword());
        return true;
    }

    public Boolean changePassword(UserInfo userInfo){
        String sql = "UPDATE UserInfo SET email = ?, password = ?";
        jdbcTemplate.update(sql, userInfo.getEmail(), userInfo.getPassword());
        return true;

    }

}
