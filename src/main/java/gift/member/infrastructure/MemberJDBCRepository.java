package gift.member.infrastructure;

import gift.exception.type.DataAccessException;
import gift.exception.type.ForbiddenException;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberJDBCRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Member> memberRowMapper;

    public MemberJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
        this.memberRowMapper = memberRowMapper();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM member WHERE email = ?";
            return jdbcTemplate.query(sql, memberRowMapper, email)
                    .stream().findFirst();
        } catch (Exception e) {
            throw new DataAccessException("회원을 조회하는 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public List<Member> findAll() {
        try {
            String sql = "SELECT * FROM member";
            return jdbcTemplate.query(sql, memberRowMapper);
        } catch (Exception e) {
            throw new DataAccessException("모든 회원을 조회하는 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public String login(Member member) {
        try {
            String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
            return jdbcTemplate.query(sql, memberRowMapper, member.getEmail(), member.getPassword())
                    .stream().findFirst()
                    .orElseThrow(() -> new ForbiddenException("로그인에 실패했습니다."))
                    .getEmail();
        } catch (ForbiddenException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("로그인 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public String join(Member member) {
        try {
            simpleJdbcInsert.execute(
                    Map.of(
                            "email", member.getEmail(),
                            "password", member.getPassword()
                    )
            );
            return member.getEmail();
        } catch (Exception e) {
            throw new DataAccessException("회원가입 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public void update(Member member) {
        try {
            String sql = "UPDATE member SET password = ? WHERE email = ?";
            jdbcTemplate.update(sql, member.getPassword(), member.getEmail());
        } catch (Exception e) {
            throw new DataAccessException("회원정보 수정 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public void delete(String email) {
        try {
            String sql = "DELETE FROM member WHERE email = ?";
            jdbcTemplate.update(sql, email);
        } catch (Exception e) {
            throw new DataAccessException("회원탈퇴 중에 문제가 발생했습니다.");
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
