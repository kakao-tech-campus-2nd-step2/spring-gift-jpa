package gift.model;

import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Member> MemberRowMapper = (Resultset, rowNum) ->
        new Member(
                Resultset.getLong("id"),
                Resultset.getString("email"),
                Resultset.getString("password")
        );

    public Optional<Member> findMemberByEmail(String email) {
        try {
            Member member = jdbcTemplate.queryForObject("SELECT * FROM members WHERE email = ?", MemberRowMapper, email);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Member saveMember(@Valid Member member) {
        Map<String, Object> parameters = Map.of(
        "email", member.getEmail(),
        "password", member.getPassword()
        );
        Long newId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Member(newId, member.getEmail(), member.getPassword());
    }

    public void updateActiveToken(Long id, String token) {
        jdbcTemplate.update("UPDATE members SET activeToken = ? WHERE id = ?", token, id);
    }

    public void invalidateToken(String token) {
        jdbcTemplate.update("UPDATE members SET activeToken = NULL WHERE activeToken = ?", token);
    }
}
