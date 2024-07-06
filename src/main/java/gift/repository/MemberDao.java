package gift.repository;

import gift.exception.LoginErrorException;
import gift.exception.MemberException;
import gift.model.Member;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member insert(Member member) {
        try {
            var sql = "INSERT INTO member (email, password) VALUES (?, ?)";
            jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
            return getMemberByEmail(member.getEmail());
        } catch (DuplicateKeyException e) {
            throw new MemberException("중복된 이메일의 회원이 존재합니다.");
        }
    }

    public Member getMemberByEmail(String email) {
        try {
            var sql = "SELECT * FROM member WHERE email=?";
            return jdbcTemplate.queryForObject(sql, memberRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            throw new LoginErrorException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
        rs.getLong("id"),
        rs.getString("email"),
        rs.getString("password")
    );

}
