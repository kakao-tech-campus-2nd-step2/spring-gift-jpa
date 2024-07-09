package gift.logout;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogoutTokenDao {
    @Autowired
    private JdbcClient jdbcClient;
    private JdbcTemplate jdbcTemplate;

    public LogoutTokenDao(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertToken(String token) {
        String sql = """
            INSERT INTO blacklist (token)
            VALUES (?)
            """;

        jdbcTemplate.update(sql, token);
    }

    public boolean findToken(String token) {
        String sql = """
            SELECT token
            FROM blacklist
            WHERE token = ?
            """;

        List<String> isToken = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> rs.getString("token"),
            token
        );

        return isToken.isEmpty();



//        Optional<String> isToken = Optional.ofNullable(inToken);
//            //jdbcClient.sql(sql).param(token).query(String.class).optional();
//        if (isToken.isEmpty()) {
//            return true;
//        }
//        else {
//            return false;
//        }
    }
}
