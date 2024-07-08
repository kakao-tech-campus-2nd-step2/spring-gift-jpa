package gift.repository;
import gift.domain.Member;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("member")
            .usingGeneratedKeyColumns("id");
    }

    public void save(Member member) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public Member findByEmail(String email) {
        try {
            var sql = "SELECT * FROM member WHERE email = ?";
            return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
                ),
                email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Member findById(Long id) {
        try {
            var sql = "SELECT * FROM member WHERE id = ?";
            return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
                ),
                id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
