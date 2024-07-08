package gift.product.repository;

import gift.product.model.Member;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public AuthRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("Member")
            .usingGeneratedKeyColumns("member_id");
    }

    public boolean existsByEmail(String email) {
        var sql = "SELECT * FROM Member WHERE email = ?";

        return !jdbcTemplate.query(sql, (resultSet, rowNum) -> 0, email).isEmpty();
    }

    public void registerMember(Member member) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", member.getEmail());
        params.put("password", member.getPassword());

        simpleJdbcInsert.execute(params);
    }

    public Member findMember(String email) {
        var sql = "SELECT member_id, password FROM Member WHERE email = ?";

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
            new Member(resultSet.getLong("member_id"),
                email,
                resultSet.getString("password")), email);
    }
}
