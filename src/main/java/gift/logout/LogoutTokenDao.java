package gift.logout;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class LogoutTokenDao {
    @Autowired
    private JdbcClient jdbcClient;

    public LogoutTokenDao(JdbcClient jdbcClient) {this.jdbcClient = jdbcClient;}

    public void insertToken(String token) {
        String sql = """
            INSERT INTO blacklist (token)
            VALUES (?)
            """;
        jdbcClient.sql(sql)
            .param(token)
            .update();
    }

    public boolean findToken(String token) {
        String sql = """
            SELECT token
            FROM blacklist
            WHERE token = ?
            """;
        Optional<String> isToken = jdbcClient.sql(sql).param(token).query(String.class).optional();
        if (isToken.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
