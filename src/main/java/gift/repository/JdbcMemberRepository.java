package gift.repository;

import gift.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, new MemberRowMapper()).stream().findAny();
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
        return member;
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        }
    }
}
