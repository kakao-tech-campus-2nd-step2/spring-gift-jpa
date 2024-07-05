package gift.repository;

import gift.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class SimpleJdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public SimpleJdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT user_id, email, password FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, userRowMapper).stream().findFirst();
    }

    @Override
    public void save(Member member) {
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    private static final RowMapper<Member> userRowMapper = (ResultSet rs, int rowNum) -> {
        Member member = new Member();
        member.setUser_id(rs.getLong("user_id"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        return member;
    };
}
