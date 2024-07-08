package gift.repository;

import gift.exception.NotFoundElementException;
import gift.model.Member;
import gift.model.MemberRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MemberJDBCRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            MemberRole.valueOf(rs.getString("role"))
    );

    public Member save(Member member) {
        var id = insertAndReturnId(member);
        return createMemberWithId(id, member);
    }

    public Member findByEmail(String email) {
        var sql = "select id, name, email, password, role from member where email = ?";
        try {
            var member = jdbcTemplate.queryForObject(sql, memberRowMapper, email);
            return member;
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundElementException(exception.getMessage());
        }
    }

    public boolean existsById(Long id) {
        var sql = "select exists(select 1 from member where id = ? limit 1)";
        var exists = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        return exists;
    }

    public boolean existsByEmail(String email) {
        var sql = "select exists(select 1 from member where email = ? limit 1)";
        var exists = jdbcTemplate.queryForObject(sql, Boolean.class, email);
        return exists;
    }

    public void deleteById(Long id) {
        var sql = "delete from member where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Long insertAndReturnId(Member member) {
        var param = new BeanPropertySqlParameterSource(member);
        return jdbcInsert.executeAndReturnKey(param).longValue();
    }

    private Member createMemberWithId(Long id, Member member) {
        return new Member(id, member.getName(), member.getEmail(), member.getPassword(), member.getRole());
    }
}
