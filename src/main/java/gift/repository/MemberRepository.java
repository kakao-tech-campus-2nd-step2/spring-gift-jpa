package gift.repository;

import gift.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) ->
            new Member(rs.getLong("id"), rs.getString("email"), rs.getString("password"));

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        List<Member> members = jdbcTemplate.query(sql, memberRowMapper, email);
        if (members.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(members.get(0));
    }

    public Member save(Member member) {
        String sql = "INSERT INTO members (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        member.setId(id);
        return member;
    }
}
