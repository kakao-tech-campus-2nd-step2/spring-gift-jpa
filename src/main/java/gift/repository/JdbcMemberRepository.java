package gift.repository;

import gift.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT user_id, email, password FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, userRowMapper).stream().findFirst();
    }

    @Override
    public Member save(Member member) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        member.setUser_id(newId.longValue());
        return member;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    private static final RowMapper<Member> userRowMapper = (ResultSet rs, int rowNum) -> {
        Member member = new Member();
        member.setUser_id(rs.getLong("user_id"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        return member;
    };
}
