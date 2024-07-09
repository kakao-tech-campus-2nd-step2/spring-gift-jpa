package gift.repository;

import gift.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public MemberRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Member member) {
        Number newId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("email", member.getEmail())
                .addValue("password", member.getPassword()));
        member.setId(newId.longValue());
    }

    public Optional<Member> findByEmail(String email) {
        try {
            Member member = jdbcTemplate.queryForObject(
                    "SELECT * FROM members WHERE email = ?",
                    new Object[]{email},
                    (rs, rowNum) -> new Member(rs.getLong("id"), rs.getString("email"), rs.getString("password"))
            );
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
