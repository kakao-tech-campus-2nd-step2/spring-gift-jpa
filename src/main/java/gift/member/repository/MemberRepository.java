package gift.member.repository;

import gift.member.model.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("members")
            .usingGeneratedKeyColumns("id");
    }

    public Optional<Member> findByEmail(String email) {
        String userEmail = "SELECT * FROM members WHERE email = ?";
        List<Member> members = jdbcTemplate.query(userEmail, new Object[]{email}, (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        });
        if (members.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(members.get(0));
    }

    public void save(Member member) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        member.setId(newId.longValue());
    }
}
