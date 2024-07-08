package gift.member.persistence.repository;

import gift.global.exception.ErrorCode;
import gift.global.exception.NotFoundException;
import gift.member.persistence.entity.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJDBCRepository implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    public MemberJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveMember(Member member) {
        var sql = "INSERT INTO member (email, password) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(
                con -> {
                    var ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, member.getEmail());
                    ps.setString(2, member.getPassword());
                    return ps;
                },
                keyHolder
            );
        } catch (DataAccessException e) {
            throw new RuntimeException("이미 가입된 email입니다.");
        }

        if(keyHolder.getKey() == null) {
            throw new RuntimeException("멤버 생성에 실패했습니다.");
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public Member getMemberByEmail(String email) {
        var sql = "SELECT * FROM member WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Member(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
                ),
                email
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Member with email " + email + " not found");
        }
    }

    @Override
    public Member getMemberById(Long id) {
        var sql = "SELECT * FROM member WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Member(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
                ),
                id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Member with id " + id + " not found");
        }
    }
}
