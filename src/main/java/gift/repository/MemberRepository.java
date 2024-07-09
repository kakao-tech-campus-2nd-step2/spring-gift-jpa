package gift.repository;

import static gift.controller.member.MemberDto.toMember;

import gift.domain.Member;
import gift.controller.member.MemberDto;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> MEMBER_ROW_MAPPER = (resultSet, rowNum) -> new Member(
        resultSet.getString("email"),
        resultSet.getString("password")
    );

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, MEMBER_ROW_MAPPER);
    }

    public Optional<Member> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM members WHERE email = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, MEMBER_ROW_MAPPER, email));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Member save(MemberDto member) {
        String sql = "INSERT INTO members (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.email(), member.password());
        return findByEmail(member.email()).get();
    }

    public Member update(String email, MemberDto member) {
        String sql = "UPDATE members SET password = ? WHERE email = ?";
        jdbcTemplate.update(sql, member.password(), email);
        return toMember(member);
    }

    public void delete(String email) {
        String sql = "DELETE FROM members WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }
}
