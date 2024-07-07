package gift.Login.repository;

import gift.Login.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        List<Member> members = jdbcTemplate.query(sql, new Object[]{email}, new MemberRowMapper());
        return members.isEmpty() ? null : members.get(0);
    }

    public void save(Member member) {
        String sql = "INSERT INTO members (id, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getId(), member.getEmail(), member.getPassword());
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(UUID.fromString(rs.getString("id")));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        }
    }
}
