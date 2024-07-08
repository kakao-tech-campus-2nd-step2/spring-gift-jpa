package gift.member.repository;

import gift.member.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Member> userRowMapper = (rs, rowNum) -> {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        return member;
    };

    public void save(Member member) {
        String sql = "INSERT INTO members (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper, email);
    }

}
