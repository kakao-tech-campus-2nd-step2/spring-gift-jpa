package gift.domain.member;

import gift.web.exception.MemberNotFoundException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private JdbcTemplate jdbcTemplate;
    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role")
        );
    }

    public Member insertMember(Member member) {
        var sql = "insert into members (email, password) values(?, ?)";
        jdbcTemplate.update(sql, member.email(), member.password());
        return getMemberByEmail(member.email());
    }

    public List<Member> findAll() {
        var sql = "select * from members";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public Member getMemberByEmail(String email) {
        var sql = "select * from members where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, memberRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    public void updateMember(Member member) {
        var sql = "update members set email = ?, password = ? where email = ?";
        jdbcTemplate.update(
            sql,
            member.email(),
            member.password(),
            member.email()
        );
    }

    public void deleteMember(String email) {
        var sql = "delete from members where email = ?";
        jdbcTemplate.update(sql, email);
    }
}
