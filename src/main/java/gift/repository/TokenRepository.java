package gift.repository;

import gift.entity.Product;
import gift.entity.Token;
import gift.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class TokenRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TokenRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("tokens")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Token newToken) {
        Map<String, Object> parameters = Map.of(
                "name", newToken.getTokenValue()
        );
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long id = newId.longValue();
        return id ;
    }

    public void delete(Token token) {
            var sql = "delete from tokens where id= ?";
            jdbcTemplate.update(sql,token.getId());
    }
}
