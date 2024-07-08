package gift.repository;

import gift.model.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
        rs.getLong("id"),
        rs.getString("email"),
        rs.getString("password")
    );

    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM members", memberRowMapper);
    }

    public Optional<Member> findById(Long id) {
        List<Member> results = jdbcTemplate.query("SELECT * FROM members WHERE id = ?", memberRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Member> findByEmail(String email) {
        List<Member> results = jdbcTemplate.query("SELECT * FROM members WHERE email = ?", memberRowMapper, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Member create(Member member) {
        jdbcTemplate.update("INSERT INTO members (email, password) VALUES (?, ?)",
            member.getEmail(), member.getPassword());
        return member;
    }

    public Member update(Member member) {
        jdbcTemplate.update("UPDATE members SET email = ?, password = ? WHERE id = ?",
            member.getEmail(), member.getPassword(), member.getId());
        return member;
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM members WHERE id = ?", id);
    }

    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM members WHERE email = ?", Integer.class, email);
        return count != null && count > 0;
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM members WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }
}
