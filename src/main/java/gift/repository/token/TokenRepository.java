package gift.repository.token;

import gift.domain.AuthToken;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TokenRepository {

    private final JdbcTemplate jdbcTemplate;

    public TokenRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String tokenSave(String token, String email){
        String sql = "INSERT INTO authtoken(token, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, token, email);
        return token;
    }

    public Optional<AuthToken> findTokenByToken(String token){
        String sql = "SELECT token, email FROM authtoken WHERE token = ?";
        List<AuthToken> authToken = jdbcTemplate.query(sql, new Object[]{token}, (rs, rowNum) -> new AuthToken(
                rs.getString("token"),
                rs.getString("email")
        ));

        if (authToken.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(authToken.getFirst());
    }

    public Optional<AuthToken> findTokenByEmail(String email){
        String sql = "SELECT token, email FROM authtoken WHERE email = ?";
        List<AuthToken> authToken = jdbcTemplate.query(sql, new Object[]{email}, (rs, rowNum) -> new AuthToken(
                rs.getString("token"),
                rs.getString("email")
        ));

        if (authToken.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(authToken.getFirst());
    }
}
